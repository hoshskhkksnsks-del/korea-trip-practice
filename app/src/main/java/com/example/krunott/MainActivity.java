package com.example.krunott;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends Activity {

LinearLayout root;  
TextToSpeech tts;  

int pink = Color.rgb(244, 117, 160);  
int blue = Color.rgb(93, 190, 230);  
int lightPink = Color.rgb(250, 190, 210);  
int dark = Color.rgb(55, 45, 50);  
int green = Color.rgb(50, 150, 90);  
int red = Color.rgb(220, 70, 70);  

int quizIndex = 0;  
int score = 0;  

@Override  
protected void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  

    tts = new TextToSpeech(this, status -> {  
        if (status == TextToSpeech.SUCCESS) {  
            int result = tts.setLanguage(Locale.US);  

            if (result == TextToSpeech.LANG_MISSING_DATA ||  
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {  

                Toast.makeText(  
                        this,  
                        "English voice data is not available.",  
                        Toast.LENGTH_LONG  
                ).show();  
            }  

            tts.setSpeechRate(0.85f);  
        }  
    });  

    showHome();  
}  

@Override  
protected void onDestroy() {  
    if (tts != null) {  
        tts.stop();  
        tts.shutdown();  
    }  

    super.onDestroy();  
}  

// =========================================================  
// HOME  
// =========================================================  

void showHome() {  

    root = baseLayout();  

    TextView title = text(  
            "🇰🇷 Korea Trip Practice",  
            30,  
            Color.WHITE  
    );  

    title.setTypeface(null, Typeface.BOLD);  
    title.setGravity(Gravity.CENTER);  

    LinearLayout header = box(pink);  
    header.setPadding(25, 30, 25, 30);  
    header.addView(title);  

    TextView subtitle = text(  
            "ฝึกภาษาอังกฤษสำหรับตอบคำถาม\nตอนผ่าน ตม.เกาหลี",  
            18,  
            Color.WHITE  
    );  

    subtitle.setGravity(Gravity.CENTER);  
    header.addView(subtitle);  

    root.addView(header);  

    addSpace(20);  

    TextView intro = text(  
            "✈️ เตรียมตัวก่อนเดินทาง",  
            24,  
            dark  
    );  

    intro.setTypeface(null, Typeface.BOLD);  
    root.addView(intro);  

    addSpace(10);  

    TextView description = text(  
            "ฝึกฟังคำถามภาษาอังกฤษ ฝึกตอบคำถาม\n"  
                    + "และเตรียมความพร้อมสำหรับการเดินทาง",  
            17,  
            Color.DKGRAY  
    );  

    root.addView(description);  

    addSpace(20);  

    Button learn = button(  
            "📖 1. เรียนรู้คำถาม",  
            pink  
    );  

    Button quiz = button(  
            "📝 2. แบบทดสอบ",  
            blue  
    );  

    Button prepare = button(  
            "✈️ 3. เตรียมตัวก่อนเดินทาง",  
            lightPink  
    );  

    root.addView(learn);  
    root.addView(quiz);  
    root.addView(prepare);  

    learn.setOnClickListener(v -> {  
        click();  
        showLessons();  
    });  

    quiz.setOnClickListener(v -> {  
        click();  
        showQuiz();  
    });  

    prepare.setOnClickListener(v -> {  
        click();  
        showPreparation();  
    });  

    addSpace(20);  

    TextView footer = text(  
            "Korea Trip Practice\nฝึกให้พร้อมก่อนเดินทาง 🇰🇷",  
            14,  
            Color.GRAY  
    );  

    footer.setGravity(Gravity.CENTER);  

    root.addView(footer);  

    setContentView(scroll(root));  
}  

// =========================================================  
// LESSONS  
// =========================================================  

void showLessons() {  

    root = baseLayout();  

    addBackButton();  

    TextView title = text(  
            "📖 คำถามที่พบบ่อย",  
            28,  
            dark  
    );  

    title.setTypeface(null, Typeface.BOLD);  
    root.addView(title);  

    addSpace(8);  

    TextView info = text(  
            "แตะ 🔊 เพื่อฟังเสียงภาษาอังกฤษ\n"  
                    + "พยายามจำคำถามและคำตอบที่ใช้บ่อย",  
            16,  
            Color.DKGRAY  
    );  

    root.addView(info);  

    addSpace(15);  

    String[][] data = {  

            {  
                    "What is the purpose of your visit?",  
                    "มาเกาหลีเพื่ออะไร?",  
                    "I am here for tourism."  
            },  

            {  
                    "How long will you stay in Korea?",  
                    "คุณจะอยู่เกาหลีกี่วัน?",  
                    "I will stay for seven days."  
            },  

            {  
                    "Where will you stay?",  
                    "คุณจะพักที่ไหน?",  
                    "I will stay at a hotel in Seoul."  
            },  

            {  
                    "What is the name of your hotel?",  
                    "โรงแรมของคุณชื่ออะไร?",  
                    "The name of my hotel is ABC Hotel."  
            },  

            {  
                    "Do you have a hotel reservation?",  
                    "คุณมีการจองโรงแรมไหม?",  
                    "Yes, I have a reservation."  
            },  

            {  
                    "How many days will you stay?",  
                    "คุณจะพักกี่วัน?",  
                    "I will stay for five days."  
            },  

            {  
                    "Is this your first time in Korea?",  
                    "นี่เป็นครั้งแรกที่คุณมาเกาหลีหรือเปล่า?",  
                    "Yes, this is my first time."  
            },  

            {  
                    "Have you been to Korea before?",  
                    "คุณเคยมาประเทศเกาหลีมาก่อนหรือไม่?",  
                    "No, I have not."  
            },  

            {  
                    "Who are you traveling with?",  
                    "คุณเดินทางมากับใคร?",  
                    "I am traveling with my partner."  
            },  

            {  
                    "Are you traveling alone?",  
                    "คุณเดินทางมาคนเดียวหรือเปล่า?",  
                    "No, I am traveling with my partner."  
            },  

            {  
                    "What do you do?",  
                    "คุณทำงานอะไร?",  
                    "I sell sausages."  
            },  

            {  
                    "Where do you work?",  
                    "คุณทำงานที่ไหน?",  
                    "I work in Thailand."  
            },  

            {  
                    "Who paid for your trip?",  
                    "ใครเป็นคนออกค่าเดินทาง?",  
                    "I paid for the trip myself."  
            },  

            {  
                    "How much money do you have?",  
                    "คุณมีเงินเท่าไหร่?",  
                    "I have enough money for my trip."  
            },  

            {  
                    "Do you have a return ticket?",  
                    "คุณมีตั๋วขากลับหรือไม่?",  
                    "Yes, I have a return ticket."  
            },  

            {  
                    "When will you return to Thailand?",  
                    "คุณจะกลับประเทศไทยเมื่อไหร่?",  
                    "I will return to Thailand next week."  
            },  

            {  
                    "Why did you come to Korea?",  
                    "ทำไมคุณถึงมาเกาหลี?",  
                    "I came to Korea for tourism."  
            },  

            {  
                    "Which places will you visit?",  
                    "คุณจะไปเที่ยวที่ไหนบ้าง?",  
                    "I will visit Seoul and Busan."  
            },  

            {  
                    "What places do you want to visit?",  
                    "คุณอยากไปเที่ยวที่ไหน?",  
                    "I want to visit Seoul."  
            },  

            {  
                    "How long is your trip?",  
                    "ทริปของคุณนานกี่วัน?",  
                    "My trip is seven days."  
            },  

            {  
                    "Do you have an itinerary?",  
                    "คุณมีแผนการเดินทางไหม?",  
                    "Yes, I have an itinerary."  
            },  

            {  
                    "Can I see your passport?",  
                    "ขอดูพาสปอร์ตของคุณได้ไหม?",  
                    "Sure. Here is my passport."  
            },  

            {  
                    "Can I see your return ticket?",  
                    "ขอดูตั๋วขากลับได้ไหม?",  
                    "Sure. Here is my return ticket."  
            },  

            {  
                    "Do you have travel insurance?",  
                    "คุณมีประกันการเดินทางไหม?",  
                    "Yes, I have travel insurance."  
            },  

            {  
                    "Do you speak English?",  
                    "คุณพูดภาษาอังกฤษได้ไหม?",  
                    "A little."  
            }  
    };  

    for (String[] item : data) {  
        addLessonCard(item);  
    }  

    setContentView(scroll(root));  
}  

void addLessonCard(String[] item) {  

    LinearLayout card = box(Color.WHITE);  
    card.setPadding(20, 20, 20, 20);  

    TextView q = text(  
            item[0],  
            20,  
            dark  
    );  

    q.setTypeface(null, Typeface.BOLD);  

    Button qSound = smallButton("🔊 ฟังคำถาม");  

    TextView thai = text(  
            "🇹🇭 " + item[1],  
            17,  
            Color.DKGRAY  
    );  

    TextView answer = text(  
            "💬 คำตอบ:\n" + item[2],  
            18,  
            green  
    );  

    Button aSound = smallButton("🔊 ฟังคำตอบ");  

    qSound.setOnClickListener(v -> {  
        click();  
        speak(item[0]);  
    });  

    aSound.setOnClickListener(v -> {  
        click();  
        speak(item[2]);  
    });  

    card.addView(q);  
    card.addView(qSound);  

    addCardSpace(card);  

    card.addView(thai);  

    addCardSpace(card);  

    card.addView(answer);  
    card.addView(aSound);  

    root.addView(card);  

    addSpace(15);  
}  

// =========================================================  
// QUIZ  
// =========================================================  

String[][] quizQuestions = {  

        {  
                "What is the purpose of your visit?",  
                "I am here for tourism.",  
                "I am here for work.",  
                "I live in Korea.",  
                "I am here for tourism."  
        },  

        {  
                "How long will you stay in Korea?",  
                "I stay Korea.",  
                "I will stay for seven days.",  
                "Korea is beautiful.",  
                "I will stay for seven days."  
        },  

        {  
                "Where will you stay?",  
                "I will stay at a hotel in Seoul.",  
                "I am from Thailand.",  
                "I stay passport.",  
                "I will stay at a hotel in Seoul."  
        },  

        {  
                "Who are you traveling with?",  
                "I am traveling with my partner.",  
                "I have a passport.",  
                "I like Korean food.",  
                "I am traveling with my partner."  
        },  

        {  
                "Do you have a return ticket?",  
                "Yes, I have a return ticket.",  
                "Yes, I am a ticket.",  
                "Yes, Korea is a ticket.",  
                "Yes, I have a return ticket."  
        },  

        {  
                "What do you do?",  
                "I sell sausages.",  
                "I am seven days.",  
                "I have a hotel.",  
                "I sell sausages."  
        },  

        {  
                "Is this your first time in Korea?",  
                "Yes, this is my first time.",  
                "Yes, I have seven days.",  
                "Yes, I am a hotel.",  
                "Yes, this is my first time."  
        },  

        {  
                "Can I see your passport?",  
                "Sure. Here is my passport.",  
                "I am from Seoul.",  
                "I stay for five days.",  
                "Sure. Here is my passport."  
        },  

        {  
                "How many days will you stay?",  
                "I will stay for five days.",  
                "I am from Thailand.",  
                "I have a ticket.",  
                "I will stay for five days."  
        },  

        {  
                "When will you return to Thailand?",  
                "I will return next week.",  
                "I return passport.",  
                "I am Korea.",  
                "I will return next week."  
        },  

        {  
                "Which places will you visit?",  
                "I will visit Seoul and Busan.",  
                "I will visit passport.",  
                "I am seven days.",  
                "I will visit Seoul and Busan."  
        },  

        {  
                "Do you have a hotel reservation?",  
                "Yes, I have a reservation.",  
                "Yes, I am a hotel.",  
                "Yes, I Korea.",  
                "Yes, I have a reservation."  
        },  

        {  
                "Are you traveling alone?",  
                "No, I am traveling with my partner.",  
                "No, I am a passport.",  
                "No, I am seven days.",  
                "No, I am traveling with my partner."  
        },  

        {  
                "Why did you come to Korea?",  
                "I came to Korea for tourism.",  
                "I came passport.",  
                "I came seven days.",  
                "I came to Korea for tourism."  
        },  

        {  
                "Do you speak English?",  
                "A little.",  
                "A hotel.",  
                "Seven days.",  
                "A little."  
        }  
};  

void showQuiz() {  

    quizIndex = 0;  
    score = 0;  

    showQuestion();  
}  

void showQuestion() {  

    root = baseLayout();  

    addBackButton();  

    TextView title = text(  
            "📝 แบบทดสอบ",  
            28,  
            dark  
    );  

    title.setTypeface(null, Typeface.BOLD);  
    root.addView(title);  

    addSpace(8);  

    TextView counter = text(  
            "ข้อ " + (quizIndex + 1)  
                    + " / "  
                    + quizQuestions.length,  
            18,  
            Color.DKGRAY  
    );  

    root.addView(counter);  

    addSpace(15);  

    String[] q = quizQuestions[quizIndex];  

    TextView question = text(  
            q[0],  
            23,  
            dark  
    );  

    question.setTypeface(null, Typeface.BOLD);  
    root.addView(question);  

    Button sound = smallButton("🔊 ฟังคำถาม");  

    sound.setOnClickListener(v -> {  
        click();  
        speak(q[0]);  
    });  

    root.addView(sound);  

    addSpace(15);  

    ArrayList<String> answers = new ArrayList<>();  

    answers.add(q[1]);  
    answers.add(q[2]);  
    answers.add(q[3]);  

    Collections.shuffle(answers);  

    for (String answer : answers) {  

        LinearLayout row = new LinearLayout(this);  

        row.setOrientation(LinearLayout.HORIZONTAL);  

        Button option = button(  
                answer,  
                Color.WHITE  
        );  

        Button voice = smallButton("🔊");  

        option.setOnClickListener(v -> {  

            click();  

            if (answer.equals(q[4])) {  

                score++;  

                animateCorrect(option);  

                Toast.makeText(  
                        this,  
                        "✅ Correct!",  
                        Toast.LENGTH_SHORT  
                ).show();  

                option.postDelayed(() -> {  

                    quizIndex++;  

                    if (quizIndex < quizQuestions.length) {  
                        showQuestion();  
                    } else {  
                        showResult();  
                    }  

                }, 900);  

            } else {  

                Toast.makeText(  
                        this,  
                        "❌ Try again",  
                        Toast.LENGTH_SHORT  
                ).show();  

                shake(option);  
            }  
        });  

        voice.setOnClickListener(v -> {  
            click();  
            speak(answer);  
        });  

        row.addView(  
                option,  
                new LinearLayout.LayoutParams(  
                        0,  
                        LinearLayout.LayoutParams.WRAP_CONTENT,  
                        1  
                )  
        );  

        row.addView(voice);  

        root.addView(row);  

        addSpace(10);  
    }  

    setContentView(scroll(root));  
}  

// =========================================================  
// RESULT  
// =========================================================  

void showResult() {  

    root = baseLayout();  

    addSpace(50);  

    TextView title = text(  
            "🎉 ทำแบบทดสอบเสร็จแล้ว!",  
            30,  
            pink  
    );  

    title.setGravity(Gravity.CENTER);  
    title.setTypeface(null, Typeface.BOLD);  

    root.addView(title);  

    addSpace(25);  

    TextView result = text(  
            "คะแนนของคุณ\n\n"  
                    + score  
                    + " / "  
                    + quizQuestions.length,  
            30,  
            dark  
    );  

    result.setGravity(Gravity.CENTER);  

    root.addView(result);  

    addSpace(30);  

    TextView message;  

    if (score == quizQuestions.length) {  

        message = text(  
                "🌟 Perfect!\nเก่งมาก!",  
                22,  
                green  
        );  

    } else if (score >= 10) {  

        message = text(  
                "👍 Good job!\nฝึกอีกนิดจะคล่องขึ้น",  
                22,  
                blue  
        );  

    } else {  

        message = text(  
                "💪 Keep practicing!\nลองฝึกคำถามอีกครั้ง",  
                22,  
                red  
        );  
    }  

    message.setGravity(Gravity.CENTER);  

    root.addView(message);  

    addSpace(30);  

    Button again = button(  
            "🔄 ทำแบบทดสอบอีกครั้ง",  
            pink  
    );  

    Button home = button(  
            "🏠 กลับหน้าหลัก",  
            blue  
    );  

    root.addView(again);  
    root.addView(home);  

    again.setOnClickListener(v -> {  
        click();  
        showQuiz();  
    });  

    home.setOnClickListener(v -> {  
        click();  
        showHome();  
    });  

    animateCorrect(result);  

    setContentView(scroll(root));  
}  

// =========================================================  
// PREPARATION  
// =========================================================  

void showPreparation() {  

    root = baseLayout();  

    addBackButton();  

    TextView title = text(  
            "✈️ เตรียมตัวก่อนเดินทาง",  
            28,  
            dark  
    );  

    title.setTypeface(null, Typeface.BOLD);  

    root.addView(title);  

    addSpace(15);  

    addInfoCard(  
            "🛂 1. เตรียมพาสปอร์ต",  
            "ตรวจสอบว่าพาสปอร์ตยังใช้งานได้และนำติดตัวไว้"  
    );  

    addInfoCard(  
            "🏨 2. เตรียมข้อมูลโรงแรม",  
            "จำชื่อโรงแรม ที่อยู่ และเก็บหลักฐานการจองไว้"  
    );  

    addInfoCard(  
            "✈️ 3. เตรียมตั๋วขากลับ",  
            "เก็บข้อมูลเที่ยวบินขากลับไว้ในโทรศัพท์"  
    );  

    addInfoCard(  
            "💰 4. เตรียมข้อมูลค่าใช้จ่าย",  
            "ควรทราบว่ามีเงินสำหรับใช้ระหว่างการเดินทางเท่าไหร่"  
    );  

    addInfoCard(  
            "🗓️ 5. เตรียมแผนการเดินทาง",  
            "จำสถานที่หลัก ๆ ที่ต้องการไปเที่ยว"  
    );  

    addInfoCard(  
            "🗣️ 6. ฝึกภาษาอังกฤษ",  
            "ฝึกตอบคำถามง่าย ๆ เช่น Tourism, Hotel และจำนวนวันที่พัก"  
    );  

    addInfoCard(  
            "📱 7. เตรียมโทรศัพท์",  
            "ชาร์จแบตให้เต็มและเก็บข้อมูลสำคัญไว้ในเครื่อง"  
    );  

    addInfoCard(  
            "😊 8. เวลาตอบคำถาม",  
            "ฟังคำถามให้จบ ตอบตามความจริง และถ้าไม่เข้าใจสามารถขอให้เจ้าหน้าที่พูดซ้ำได้"  
    );  

    addSpace(20);  

    Button practice = button(  
            "📝 ไปฝึกทำแบบทดสอบ",  
            pink  
    );  

    root.addView(practice);  

    practice.setOnClickListener(v -> {  
        click();  
        showQuiz();  
    });  

    setContentView(scroll(root));  
}  

void addInfoCard(String title, String description) {  

    LinearLayout card = box(Color.WHITE);  

    card.setPadding(20, 20, 20, 20);  

    TextView t = text(  
            title,  
            20,  
            dark  
    );  

    t.setTypeface(null, Typeface.BOLD);  

    TextView d = text(  
            description,  
            16,  
            Color.DKGRAY  
    );  

    card.addView(t);  

    addCardSpace(card);  

    card.addView(d);  

    root.addView(card);  

    addSpace(12);  
}  

// =========================================================  
// UI HELPERS  
// =========================================================  

LinearLayout baseLayout() {  

    LinearLayout layout = new LinearLayout(this);  

    layout.setOrientation(LinearLayout.VERTICAL);  

    layout.setPadding(  
            20,  
            20,  
            20,  
            30  
    );  

    layout.setBackgroundColor(  
            Color.rgb(248, 246, 248)  
    );  

    return layout;  
}  

LinearLayout box(int color) {  

    LinearLayout layout = new LinearLayout(this);  

    layout.setOrientation(  
            LinearLayout.VERTICAL  
    );  

    layout.setBackgroundColor(color);  

    return layout;  
}  

TextView text(  
        String value,  
        int size,  
        int color  
) {  

    TextView t = new TextView(this);  

    t.setText(value);  
    t.setTextSize(size);  
    t.setTextColor(color);  

    t.setPadding(  
            5,  
            8,  
            5,  
            8  
    );  

    return t;  
}  

Button button(  
        String value,  
        int color  
) {  

    Button b = new Button(this);  

    b.setText(value);  
    b.setTextSize(17);  
    b.setTextColor(dark);  

    b.setBackgroundColor(color);  

    LinearLayout.LayoutParams params =  
            new LinearLayout.LayoutParams(  
                    LinearLayout.LayoutParams.MATCH_PARENT,  
                    LinearLayout.LayoutParams.WRAP_CONTENT  
            );  

    params.setMargins(  
            0,  
            7,  
            0,  
            7  
    );  

    b.setLayoutParams(params);  

    return b;  
}  

Button smallButton(String value) {  

    Button b = new Button(this);  

    b.setText(value);  
    b.setTextSize(14);  

    b.setPadding(  
            10,  
            3,  
            10,  
            3  
    );  

    return b;  
}  

ScrollView scroll(View view) {  

    ScrollView s = new ScrollView(this);  

    s.setFillViewport(true);  

    s.addView(view);  

    return s;  
}  

void addBackButton() {  

    Button back = smallButton(  
            "← กลับ"  
    );  

    root.addView(back);  

    back.setOnClickListener(v -> {  
        click();  
        showHome();  
    });  

    addSpace(10);  
}  

void addSpace(int height) {  

    Space space = new Space(this);  

    root.addView(  
            space,  
            new LinearLayout.LayoutParams(  
                    1,  
                    height  
            )  
    );  
}  

void addCardSpace(LinearLayout layout) {  

    Space space = new Space(this);  

    layout.addView(  
            space,  
            new LinearLayout.LayoutParams(  
                    1,  
                    8  
            )  
    );  
}  

// =========================================================  
// TTS  
// =========================================================  

void speak(String text) {  

    if (tts != null) {  

        tts.speak(  
                text,  
                TextToSpeech.QUEUE_FLUSH,  
                null,  
                "KOREA_TRIP_TTS"  
        );  
    }  
}  

// =========================================================  
// SOUND / ANIMATION  
// =========================================================  

void click() {  

    // reserved for future sound effects  
}  

void animateCorrect(View view) {  

    ScaleAnimation animation =  
            new ScaleAnimation(  
                    1.0f,  
                    1.08f,  
                    1.0f,  
                    1.08f,  
                    Animation.RELATIVE_TO_SELF,  
                    0.5f,  
                    Animation.RELATIVE_TO_SELF,  
                    0.5f  
            );  

    animation.setDuration(180);  
    animation.setRepeatCount(2);  
    animation.setRepeatMode(  
            Animation.REVERSE  
    );  

    view.startAnimation(animation);  
}  

void shake(View view) {  

    AlphaAnimation animation =  
            new AlphaAnimation(  
                    1.0f,  
                    0.3f  
            );  

    animation.setDuration(120);  
    animation.setRepeatCount(3);  
    animation.setRepeatMode(  
            Animation.REVERSE  
    );  

    view.startAnimation(animation);  
}

}
