/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.speech.tts.cts;

import android.media.AudioFormat;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;

/**
 * Stub implementation of {@link TextToSpeechService}. Used for testing the
 * TTS engine API.
 */
public class StubTextToSpeechService extends TextToSpeechService {

    public static final String TEXT_STREAM = "stream";
    public static final String TEXT_COMPLETE = "complete";

    @Override
    protected String[] onGetLanguage() {
        return new String[] { "eng", "USA", "" };
    }

    @Override
    protected int onIsLanguageAvailable(String lang, String country, String variant) {
        return TextToSpeech.LANG_AVAILABLE;
    }

    @Override
    protected int onLoadLanguage(String lang, String country, String variant) {
        return TextToSpeech.LANG_AVAILABLE;
    }

    @Override
    protected void onStop() {
    }

    @Override
    protected void onSynthesizeText(SynthesisRequest request) {
        if (TEXT_STREAM.equals(request.getText())) {
            synthesizeStreaming(request);
        } else {
            synthesizeComplete(request);
        }
    }

    private void synthesizeStreaming(SynthesisRequest request) {
        if (request.start(16000, AudioFormat.ENCODING_PCM_16BIT, 1) != TextToSpeech.SUCCESS) {
            return;
        }
        byte[] data = { 0x01, 0x2 };
        if (request.audioAvailable(data, 0, data.length) != TextToSpeech.SUCCESS) {
            return;
        }
        if (request.done() != TextToSpeech.SUCCESS) {
            return;
        }
    }

    private void synthesizeComplete(SynthesisRequest request) {
        byte[] data = { 0x01, 0x2 };
        if (request.completeAudioAvailable(16000, AudioFormat.ENCODING_PCM_16BIT, 1,
                data, 0, data.length) != TextToSpeech.SUCCESS) {
            return;
        }
    }

}
