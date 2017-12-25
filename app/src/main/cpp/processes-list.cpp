#include <jni.h>
#include <iostream>
#include <stdio.h>
#include <string>

extern "C" {
    JNIEXPORT jstring JNICALL
    Java_mtuci_nikitakutselay_mobileapplicationprogrammingcourse_ProcessesListLoader_getProcessesList(
            JNIEnv *env, jobject instance) {
        FILE *processList;

        char buffer[128];
        std::string result;

        if (!(processList = popen("ps", "r"))) {
            return env->NewStringUTF("Error");
        }

        while (fgets(buffer, sizeof(buffer), processList) != NULL) {
            result += buffer;
        }

        return env->NewStringUTF(result.c_str());
    }
}
