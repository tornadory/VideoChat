/*
 * VideoManager.cpp
 *
 *  Created on: 2016年7月11日
 *      Author: zzh
 */

#include<api/VideoManager.h>
#include <utils/XUtils.h>

VideoManager::VideoManager(RtpManager * rtpManager) :
		pRtpManager(rtpManager), pEncodeI420Buffer(XNULL), pDecodeYv12Buffer(
		XNULL), pDecodeI420Buffer(XNULL), nEncodeWidth(0), nEncodeFps(0), nEncodeHeight(
				0), nDecodeWidth(0), nDecodeHeight(0), nDecodeFps(0), pEncodeRawI420Buffer(
		XNULL) {
	this->pEncoder = new H264Encoder();
	this->pDecoder = new H264Decoder();
	pthread_mutex_init(&mLock, XNULL);
#ifdef __ANDROID__
	nGlViewWidth=0;
	nGlViewHeight=0;
	pGlHelper=XNULL;
#endif
}

VideoManager::~VideoManager() {
	pthread_mutex_destroy(&mLock);
	delete this->pEncoder;
	delete this->pDecoder;
}

void VideoManager::initEncoder(int width, int height, int fps) {
	pthread_mutex_lock(&mLock);
	this->pEncoder->set(width, height, fps, VIDEO_BITRATE);
	this->pEncoder->open();
	if (this->pEncodeRawI420Buffer == XNULL) {
		//this->pEncodeRawI420Buffer=new cuar
	}
	if (this->pEncodeI420Buffer == XNULL) {
		this->pEncodeI420Buffer = new uchar[width * height * 3 / 2];
	} else {
		DLOG("memset2:%d\n", pEncodeI420Buffer);
		memset(this->pEncodeI420Buffer, 0, width * height * 3 / 2);
		DLOG("memset2:%d\n", pEncodeI420Buffer);
	}
	this->nEncodeWidth = width;
	this->nEncodeHeight = height;
	this->nEncodeFps = fps;
	pthread_mutex_unlock(&mLock);
	LOG("Encoder Init, Width=%d, Height=%d, FPS=%d.\n", width, height, fps);
}

void VideoManager::deinitEncoder() {
	DLOG("deinitEncoder1c\n");
	pthread_mutex_lock(&mLock);
	this->pEncoder->close();
	DLOG("deinitEncoder11\n");
	/*
	 if (this->pEncodeI420Buffer != XNULL) {
	 delete[] this->pEncodeI420Buffer;
	 DLOG("deinitEncoder112\n");
	 this->pEncodeI420Buffer = XNULL;
	 }*/
	this->pEncodeI420Buffer = XNULL;
	DLOG("deinitEncoder12\n");
	this->nEncodeWidth = 0;
	this->nEncodeHeight = 0;
	this->nEncodeFps = 0;
	pthread_mutex_unlock(&mLock);
	DLOG("deinitEncoder2\n");
}

void VideoManager::initDecoder(int width, int height, int fps) {
	pthread_mutex_lock(&mLock);
	this->pDecoder->set(width, height, fps);
	this->pDecodeYv12Buffer = new uchar[width * height * 3 / 2];
	this->pDecodeI420Buffer = new uchar[width * height * 3 / 2];
	this->nDecodeWidth = width;
	this->nDecodeHeight = height;
	this->nDecodeFps = fps;
	pthread_mutex_unlock(&mLock);
	LOG("Decoder Init, Width=%d, Height=%d, FPS=%d.\n", width, height, fps);
#ifdef __ANDROID__
	//this->initGlHepler(this->nGlViewWidth, this->nGlViewHeight);
#endif
}

void VideoManager::deinitDecoder() {
	DLOG("deinitDecoder1\n");
	pthread_mutex_lock(&mLock);
	if (this->pDecodeYv12Buffer != XNULL) {
		delete[] this->pDecodeYv12Buffer;
		this->pDecodeYv12Buffer = XNULL;
		delete[] this->pDecodeI420Buffer;
		this->pDecodeI420Buffer = XNULL;
	}
	this->nDecodeWidth = 0;
	this->nDecodeHeight = 0;
	this->nDecodeFps = 0;
	pthread_mutex_unlock(&mLock);
	DLOG("deinitDecoder2\n");
}

int VideoManager::onYv12FramePushed(uchar *yv12Frame) {
	x264_nal_t *nals;
	int nnal;
	int bytes = 0;
	pthread_mutex_lock(&mLock);
	if (!this->pEncoder->isOpen()) {
		return bytes;
	}
	lint t1 = XUtils::currentMilliSeconds();
	VideoManager::yv12ToI420(this->pEncodeI420Buffer, yv12Frame,
			this->nEncodeWidth, this->nEncodeHeight);
	int fs = this->pEncoder->encode(&nals, &nnal, this->pEncodeI420Buffer);
	lint t2 = XUtils::currentMilliSeconds();
	//DLOG("Encode Duration:%d\n",t2-t1);
	if (fs > 0) {
		for (int i = 0; i < nnal; ++i) { //将编码数据写入文件.
			//LOG("A Pushed Frame Encoded, Index=%d, Size=%d.\n", i,nals[i].i_payload);
			this->pRtpManager->sendRtp(RtpType::H264,
					(uchar *) nals[i].p_payload, nals[i].i_payload);
			bytes += nals[i].i_payload;
		}
	}
	pthread_mutex_unlock(&mLock);
	return bytes;
}

void VideoManager::onH264FrameRecved(uchar *h264Buffer, size_t h264Length) {
#ifdef __ANDROID__
	if (this->pGlHelper == XNULL) {
		return;
	}
#endif

	int frameFinished = 0;
	pthread_mutex_lock(&mLock);
	if (!this->pDecoder->isOpen()) {
		return;
	}
	this->pDecoder->decode((uchar *) h264Buffer, h264Length, &frameFinished,
			this->pDecodeI420Buffer);
	if (frameFinished) {
		//LOG("A Recved H264 Frame Decoded, Size=%d.\n", h264Length);
#ifdef __ANDROID__
		this->pGlHelper->write(this->pDecodeI420Buffer,
				this->nDecodeWidth * this->nDecodeHeight * 3 / 2);
#endif
		/*
		 VideoManager::i420ToYv12(this->pDecodeYv12Buffer,
		 this->pDecodeI420Buffer, this->mDecodeWidth,
		 this->mDecodeHeight);
		 */
	}
	pthread_mutex_unlock(&mLock);

}

void VideoManager::yv12ToI420(uchar *i420Buffer, const uchar *yv12Buffer,
		int width, int height) {
	memcpy(i420Buffer, yv12Buffer, width * height);
	memcpy(i420Buffer + width * height, yv12Buffer + width * height * 5 / 4,
			width * height / 4);
	memcpy(i420Buffer + width * height * 5 / 4, yv12Buffer + width * height,
			width * height / 4);
}

void VideoManager::i420ToYv12(uchar *yv12Buffer, const uchar *i420Buffer,
		int width, int height) {
	memcpy(yv12Buffer, i420Buffer, width * height);
	memcpy(yv12Buffer + width * height * 5 / 4, i420Buffer + width * height,
			width * height / 4);
	memcpy(yv12Buffer + width * height, i420Buffer + width * height * 5 / 4,
			width * height / 4);
}

void VideoManager::i420spRotate90(uchar *dst, const uchar *src, int srcWidth,
		int srcHeight) {
	static int nWidth = 0, nHeight = 0;
	static int wh = 0;
	static int uvHeight = 0;
	if (srcWidth != nWidth || srcHeight != nHeight) {
		nWidth = srcWidth;
		nHeight = srcHeight;
		wh = srcWidth * srcHeight;
		uvHeight = srcHeight >> 1;			//uvHeight = height / 2
	}

	//旋转Y
	int k = 0;
	for (int i = 0; i < srcWidth; i++) {
		int nPos = 0;
		for (int j = 0; j < srcHeight; j++) {
			dst[k] = src[nPos + i];
			k++;
			nPos += srcWidth;
		}
	}

	for (int i = 0; i < srcWidth; i += 2) {
		int nPos = wh;
		for (int j = 0; j < uvHeight; j++) {
			dst[k] = src[nPos + i];
			dst[k + 1] = src[nPos + i + 1];
			k += 2;
			nPos += srcWidth;
		}
	}
	return;
}

#ifdef __ANDROID__
void VideoManager::initGlHepler(int viewWidth, int viewHeight,const char *buildModel) {
	this->nGlViewWidth = viewWidth;
	this->nGlViewHeight = viewHeight;
	XASSERT(nGlViewWidth>0&&nDecodeWidth>0,"Local < 0 or Remote < 0\n");
	if (this->pGlHelper != XNULL) {
		delete this->pGlHelper;
		this->pGlHelper = XNULL;
	}
	this->pGlHelper = new GlHelper(this->nGlViewWidth, this->nGlViewHeight,
			this->nDecodeWidth, this->nDecodeHeight,buildModel);
	if (this->nGlViewWidth > 0 && this->nDecodeWidth > 0) {
	}
}

void VideoManager::deinitGlHelper() {
	if (this->pGlHelper != XNULL) {
		delete this->pGlHelper;
		this->pGlHelper = XNULL;
	}
	this->nGlViewHeight = 0;
	this->nGlViewWidth = 0;
}

void VideoManager::render() {
	if (this->pGlHelper != XNULL) {
		this->pGlHelper->render();
	}
}
#endif
