package org.techtown.example.expandablelistview;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class TextToSpeech_yogat {

    Context context;
    private static TextToSpeech tts;
    public TextToSpeech_yogat(Context context){
        this.context = context;
        tts= initTTS();
    }

    private TextToSpeech initTTS(){
            tts = new TextToSpeech(context,new TextToSpeech.OnInitListener(){
                @Override
                public void onInit(int status) {
                    if(status == TextToSpeech.SUCCESS){
                        int language = tts.setLanguage(Locale.KOREAN);
                        if(language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED){
                            Toast.makeText(context,"지원하지 않는 언어입니다",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(context,"작업실패",Toast.LENGTH_LONG).show();

                    }
                }
            });


        return tts;

    }


    public void sayText(String responseData){
        tts.setSpeechRate(0.9f);
        tts.speak(responseData, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void stopTTS() {
        if (tts != null) {
            tts.shutdown();
            tts.stop();
            tts = null;
        }
    }

}
