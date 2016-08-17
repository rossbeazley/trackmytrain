#include <jni.h>
#include <string.h>


jstring Java_uk_co_rossbeazley_trackmytrain_android_jni_Binding_stringFromJNI( JNIEnv* env, jobject thiz ) {
    return (*env)->NewStringUTF(env, "hi");
}
