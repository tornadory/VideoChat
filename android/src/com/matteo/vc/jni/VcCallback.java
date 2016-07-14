/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.10
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.matteo.vc.jni;

public class VcCallback {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected VcCallback(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(VcCallback obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        VCJNI.delete_VcCallback(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    VCJNI.VcCallback_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    VCJNI.VcCallback_change_ownership(this, swigCPtr, true);
  }

  public void onIncoming(VcCall arg0) {
    if (getClass() == VcCallback.class) VCJNI.VcCallback_onIncoming(swigCPtr, this, VcCall.getCPtr(arg0), arg0); else VCJNI.VcCallback_onIncomingSwigExplicitVcCallback(swigCPtr, this, VcCall.getCPtr(arg0), arg0);
  }

  public void onConfirmed(VcCall arg0) {
    if (getClass() == VcCallback.class) VCJNI.VcCallback_onConfirmed(swigCPtr, this, VcCall.getCPtr(arg0), arg0); else VCJNI.VcCallback_onConfirmedSwigExplicitVcCallback(swigCPtr, this, VcCall.getCPtr(arg0), arg0);
  }

  public void onEstablished(VcCall arg0) {
    if (getClass() == VcCallback.class) VCJNI.VcCallback_onEstablished(swigCPtr, this, VcCall.getCPtr(arg0), arg0); else VCJNI.VcCallback_onEstablishedSwigExplicitVcCallback(swigCPtr, this, VcCall.getCPtr(arg0), arg0);
  }

  public void onOutgoFail(VcCall arg0) {
    if (getClass() == VcCallback.class) VCJNI.VcCallback_onOutgoFail(swigCPtr, this, VcCall.getCPtr(arg0), arg0); else VCJNI.VcCallback_onOutgoFailSwigExplicitVcCallback(swigCPtr, this, VcCall.getCPtr(arg0), arg0);
  }

  public void onTimeout(VcCall arg0) {
    if (getClass() == VcCallback.class) VCJNI.VcCallback_onTimeout(swigCPtr, this, VcCall.getCPtr(arg0), arg0); else VCJNI.VcCallback_onTimeoutSwigExplicitVcCallback(swigCPtr, this, VcCall.getCPtr(arg0), arg0);
  }

  public void onDisconnect(VcCall arg0) {
    if (getClass() == VcCallback.class) VCJNI.VcCallback_onDisconnect(swigCPtr, this, VcCall.getCPtr(arg0), arg0); else VCJNI.VcCallback_onDisconnectSwigExplicitVcCallback(swigCPtr, this, VcCall.getCPtr(arg0), arg0);
  }

  public VcCallback() {
    this(VCJNI.new_VcCallback(), true);
    VCJNI.VcCallback_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

}