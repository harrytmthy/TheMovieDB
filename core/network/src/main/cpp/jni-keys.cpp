/*
 * Copyright 2022 harrytmthy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_timothy_themoviedb_network_NetworkModule_getTmdbApiKey(JNIEnv *env, jobject object) {
    std::string key = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNGE0NTQ0NzU3Y2ViZjljZTkzMmQ0ODRlOTliZGVmZCIsInN1YiI6IjVkZWYwNTBkMDI1NzY0MDAxNjU1ZWQ4ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MugHk0Heo-k38j7yIO6r_MObiEQHlwXRinR-7nwRPpw";
    return env->NewStringUTF(key.c_str());
}