#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_touhidapps_ndk_1tutorial_MainActivity_stringFromJNI(JNIEnv *env, jobject ) {
    std::string hello = "Touhid Apps Learn";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_touhidapps_ndk_1tutorial_MainActivity_getMyApiKey(JNIEnv *env, jobject) {
    std::string myApiKey = "aieo44ldkfjosiou8739kjsdfs89dofu9";
    return env->NewStringUTF(myApiKey.c_str());
}