/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.10
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.matteo.vc.jni;

public class VCJNI {
  public final static native int CALL_ACTION_ANSWER_get();
  public final static native int VcManager_makeCall(long jarg1, VcManager jarg1_, long jarg2, int jarg3, int jarg4, int jarg5);
  public final static native void VcManager_addFriend(long jarg1, VcManager jarg1_, long jarg2, String jarg3, int jarg4);
  public final static native long VcManager_getCurrentCall(long jarg1, VcManager jarg1_);
  public final static native void VcManager_setCallback(long jarg1, VcManager jarg1_, long jarg2, VcCallback jarg2_);
  public final static native void VcManager_handleCall(long jarg1, VcManager jarg1_, long jarg2, VcCall jarg2_, int jarg3);
  public final static native void VcManager_pushYv12Frame(long jarg1, VcManager jarg1_, String jarg2, long jarg3);
  public final static native void VcManager_setMyCameraParameters(long jarg1, VcManager jarg1_, int jarg2, int jarg3, int jarg4);
  public final static native long VcManager_init__SWIG_0(String jarg1, int jarg2, long jarg3);
  public final static native long VcManager_init__SWIG_1(String jarg1, int jarg2);
  public final static native void VcManager_deinit();
  public final static native long VcManager_get();
  public final static native long new_VcFriend(long jarg1, String jarg2, int jarg3);
  public final static native void delete_VcFriend(long jarg1);
  public final static native int VcFriend_getSsrc(long jarg1, VcFriend jarg1_);
  public final static native String VcFriend_getIp(long jarg1, VcFriend jarg1_);
  public final static native int VcFriend_getPort(long jarg1, VcFriend jarg1_);
  public final static native int UNKNOW_get();
  public final static native int ESTABLISHING_get();
  public final static native int ESTABLISHED_get();
  public final static native int CONFIRMED_get();
  public final static native int DISCONNECT_get();
  public final static native long new_VcCall__SWIG_0(long jarg1, VcFriend jarg1_, int jarg2, boolean jarg3);
  public final static native long new_VcCall__SWIG_1(long jarg1, VcFriend jarg1_, int jarg2);
  public final static native long new_VcCall__SWIG_2(long jarg1, VcFriend jarg1_);
  public final static native void delete_VcCall(long jarg1);
  public final static native int VcCall_getId(long jarg1, VcCall jarg1_);
  public final static native long VcCall_getFriend(long jarg1, VcCall jarg1_);
  public final static native int VcCall_getState(long jarg1, VcCall jarg1_);
  public final static native boolean VcCall_isOutgo(long jarg1, VcCall jarg1_);
  public final static native void VcCallback_onIncoming(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onIncomingSwigExplicitVcCallback(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onConfirmed(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onConfirmedSwigExplicitVcCallback(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onEstablished(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onEstablishedSwigExplicitVcCallback(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onOutgoFail(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onOutgoFailSwigExplicitVcCallback(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onTimeout(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onTimeoutSwigExplicitVcCallback(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onDisconnect(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native void VcCallback_onDisconnectSwigExplicitVcCallback(long jarg1, VcCallback jarg1_, long jarg2, VcCall jarg2_);
  public final static native long new_VcCallback();
  public final static native void delete_VcCallback(long jarg1);
  public final static native void VcCallback_director_connect(VcCallback obj, long cptr, boolean mem_own, boolean weak_global);
  public final static native void VcCallback_change_ownership(VcCallback obj, long cptr, boolean take_or_release);
  public final static native void pushYv12Frame(byte[] jarg1);

  public static void SwigDirector_VcCallback_onIncoming(VcCallback jself, long arg0) {
    jself.onIncoming((arg0 == 0) ? null : new VcCall(arg0, false));
  }
  public static void SwigDirector_VcCallback_onConfirmed(VcCallback jself, long arg0) {
    jself.onConfirmed((arg0 == 0) ? null : new VcCall(arg0, false));
  }
  public static void SwigDirector_VcCallback_onEstablished(VcCallback jself, long arg0) {
    jself.onEstablished((arg0 == 0) ? null : new VcCall(arg0, false));
  }
  public static void SwigDirector_VcCallback_onOutgoFail(VcCallback jself, long arg0) {
    jself.onOutgoFail((arg0 == 0) ? null : new VcCall(arg0, false));
  }
  public static void SwigDirector_VcCallback_onTimeout(VcCallback jself, long arg0) {
    jself.onTimeout((arg0 == 0) ? null : new VcCall(arg0, false));
  }
  public static void SwigDirector_VcCallback_onDisconnect(VcCallback jself, long arg0) {
    jself.onDisconnect((arg0 == 0) ? null : new VcCall(arg0, false));
  }

  private final static native void swig_module_init();
  static {
    swig_module_init();
  }
}