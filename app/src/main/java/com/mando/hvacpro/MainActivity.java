
package com.mando.hvacpro;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    private final int bg = Color.rgb(8, 12, 27);
    private final int panel = Color.rgb(20, 29, 49);
    private final int cyan = Color.rgb(0, 220, 255);
    private final int white = Color.WHITE;
    private final int muted = Color.rgb(195, 205, 220);

    private LinearLayout content;
    private Runnable backAction = () -> showHome();

    private static class Fault {
        String category;
        String title;
        String keywords;
        String details;

        Fault(String category, String title,
              String keywords, String details) {
            this.category = category;
            this.title = title;
            this.keywords = keywords;
            this.details = details;
        }
    }

    private final List<Fault> faults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        loadKnowledge();
        showHome();
    }

    private void loadKnowledge() {
        faults.add(new Fault("ac",
                "التكييف لا يبرد",
                "ضعف تبريد هواء دافئ فريون فلتر مكثف",
                "الأسباب المحتملة:\n" +
                "1- اتساخ الفلاتر أو ضعف مرور الهواء.\n" +
                "2- اتساخ الوحدة الخارجية أو ضعف التهوية.\n" +
                "3- خلل بالمروحة أو الحساسات أو دائرة التحكم.\n" +
                "4- نقص شحنة وسيط التبريد بسبب تسريب محتمل.\n\n" +
                "خطوات الفحص:\n" +
                "• اضبط الوضع على COOL ودرجة أقل من حرارة الغرفة.\n" +
                "• افحص الفلاتر ونظفها بعد فصل الكهرباء.\n" +
                "• لاحظ هل المروحتان تعملان وهل الوحدة الخارجية جيدة التهوية.\n" +
                "• الفني يقيس درجات الحرارة والتيار والضغوط حسب نوع الجهاز.\n\n" +
                "الحل يتحدد بعد القياس. لا تضف فريون لمجرد ضعف التبريد؛ ابحث عن سبب الخلل والتسريب أولًا."));

        faults.add(new Fault("ac",
                "التكييف لا يعمل نهائيًا",
                "الكهرباء باور فيوز قاطع شاشة",
                "الأسباب المحتملة:\n" +
                "1- انقطاع التغذية أو فصل القاطع.\n" +
                "2- مشكلة في الكابل أو التوصيلات.\n" +
                "3- عطل بلوحة التحكم أو دائرة التغذية.\n\n" +
                "الفحص:\n" +
                "• تأكد من القاطع الخارجي دون فتح أجزاء كهربائية.\n" +
                "• افحص البطاريات وإعدادات الريموت إن كان الجهاز يستجيب له.\n" +
                "• إذا لم تظهر أي علامة تشغيل، يحتاج الفني لقياس التغذية وفحص اللوحة.\n\n" +
                "افصل الكهرباء عند وجود رائحة احتراق أو سخونة أو شرر."));

        faults.add(new Fault("ac",
                "الكمبروسر لا يبدأ",
                "ضاغط كباستور مكثف أوفرلود إنفرتر",
                "الأسباب المحتملة:\n" +
                "1- عدم وصول أمر التشغيل أو وجود حماية مفعلة.\n" +
                "2- مشكلة في مكثف التشغيل في الأنواع التي تستخدمه.\n" +
                "3- خلل بالريلاي أو الأوفرلود أو الأسلاك.\n" +
                "4- عطل بالكمبروسر أو دائرة الإنفرتر.\n\n" +
                "الفحص:\n" +
                "• سجّل أي كود خطأ أو لمبات وامضة.\n" +
                "• الفني يقيس الجهد والتيار ويفحص المكثف والحمايات حسب تصميم الجهاز.\n" +
                "• أجهزة الإنفرتر تختلف عن الأجهزة التقليدية ولا ينطبق عليها نفس اختبار المكثف دائمًا.\n\n" +
                "لا تقصر أطراف الحماية ولا توصل الكمبروسر مباشرة بالكهرباء."));

        faults.add(new Fault("ac",
                "تكوّن ثلج على المواسير أو المبخر",
                "ثلج تجمد هواء فريون فلتر مروحة",
                "الأسباب المحتملة:\n" +
                "1- فلتر مسدود أو ضعف تدفق الهواء.\n" +
                "2- مشكلة بالمروحة أو المبخر المتسخ.\n" +
                "3- نقص وسيط التبريد أو خلل آخر بالدائرة.\n\n" +
                "الفحص:\n" +
                "• أوقف التبريد واتبع دليل الجهاز لإذابة الثلج بأمان.\n" +
                "• نظف الفلاتر بعد فصل الكهرباء.\n" +
                "• إذا تكرر الثلج، يلزم فحص تدفق الهواء ودرجات الحرارة والدائرة بواسطة فني.\n\n" +
                "لا تكسر الثلج بأداة حادة ولا تستخدم لهبًا."));

        faults.add(new Fault("ac",
                "نزول مياه من الوحدة الداخلية",
                "مياه تسريب صرف خرطوم حوض",
                "الأسباب المحتملة:\n" +
                "1- انسداد خرطوم الصرف.\n" +
                "2- عدم استواء الوحدة أو تحرك خرطوم الصرف.\n" +
                "3- اتساخ حوض التكثيف أو تجمد المبخر.\n\n" +
                "الفحص:\n" +
                "• افصل الكهرباء إذا وصلت المياه إلى أجزاء كهربائية.\n" +
                "• افحص الجزء الظاهر من خرطوم الصرف دون فك التوصيلات.\n" +
                "• إذا كان الانسداد داخليًا أو تكرر التسريب، يحتاج الجهاز إلى تنظيف وفحص تركيب الصرف.\n\n" +
                "لا تشغّل الجهاز إذا كانت المياه تصل إلى الكهرباء."));

        faults.add(new Fault("ac",
                "مروحة الوحدة لا تعمل",
                "مروحة موتور محرك لوحة حساس",
                "الأسباب المحتملة:\n" +
                "1- وضع التشغيل أو تأخير طبيعي في بدء المروحة.\n" +
                "2- انسداد ميكانيكي أو تلف بالمحرك.\n" +
                "3- مشكلة في التغذية أو المكثف في بعض الأنواع أو لوحة التحكم.\n\n" +
                "الفحص:\n" +
                "• انتظر قليلًا وتحقق من وضع التشغيل.\n" +
                "• لا تدخل يدك أو أداة قرب المروحة أثناء توصيل الكهرباء.\n" +
                "• الفني يفحص المحرك والتوصيلات ودائرة التحكم حسب نوع الجهاز."));

        faults.add(new Fault("ac",
                "التكييف يفصل ويشتغل كثيرًا",
                "فصل تشغيل حماية حرارة حساس قاطع",
                "الأسباب المحتملة:\n" +
                "1- وصول الغرفة إلى الدرجة المضبوطة أو تغير وضع التشغيل.\n" +
                "2- ضعف التهوية أو اتساخ الفلاتر والمبادل الحراري.\n" +
                "3- خلل بحساس الحرارة أو التغذية أو الحمايات.\n\n" +
                "الفحص:\n" +
                "• تحقق من الإعدادات ونظافة الفلاتر.\n" +
                "• لاحظ مدة التشغيل ورسائل الخطأ.\n" +
                "• يحتاج الفني إلى قياس التيار ودرجات الحرارة ومعرفة سبب فصل الحماية.\n\n" +
                "لا تلغِ الحماية لإجبار الجهاز على العمل."));

        faults.add(new Fault("fridge",
                "الثلاجة لا تبرد",
                "ثلاجة تبريد فريزر كمبروسر فريون",
                "الأسباب المحتملة:\n" +
                "1- إعداد حرارة غير مناسب أو ضعف التهوية حول الثلاجة.\n" +
                "2- اتساخ المكثف أو خلل بالمروحة حسب تصميم الجهاز.\n" +
                "3- عطل بنظام إذابة الثلج في بعض ثلاجات No Frost.\n" +
                "4- خلل بدائرة التبريد أو الكمبروسر.\n\n" +
                "الفحص:\n" +
                "• تأكد من الإعدادات وإغلاق الباب وسلامة الجوان.\n" +
                "• لا تسد فتحات الهواء داخل الثلاجة.\n" +
                "• لاحظ هل الكمبروسر والمراوح يعملان، دون لمس أجزاء ساخنة أو مكشوفة.\n" +
                "• الفني يحدد السبب بقياس درجات الحرارة وفحص الدائرة.\n\n" +
                "حافظ على الطعام سريع التلف باردًا، وانقله لمكان آمن إذا ارتفعت حرارته."));

        faults.add(new Fault("fridge",
                "الكمبروسر يعمل باستمرار",
                "ثلاجة تشغيل مستمر حرارة جوان باب",
                "الأسباب المحتملة:\n" +
                "1- كثرة فتح الباب أو ضعف إحكام الجوان.\n" +
                "2- وضع كمية كبيرة من الطعام الدافئ.\n" +
                "3- ضعف التهوية أو اتساخ المكثف.\n" +
                "4- خلل بنظام التحكم أو دائرة التبريد.\n\n" +
                "الفحص:\n" +
                "• افحص إغلاق الباب والجوان والإعدادات.\n" +
                "• اترك مساحة للتهوية حول الجهاز.\n" +
                "• إذا استمر التشغيل مع ضعف التبريد، اطلب فحصًا فنيًا."));

        faults.add(new Fault("fridge",
                "تراكم ثلج زائد داخل الفريزر",
                "ثلج نو فروست ديفروست سخان حساس",
                "الأسباب المحتملة:\n" +
                "1- باب لا يغلق جيدًا أو جوان تالف.\n" +
                "2- دخول رطوبة بسبب فتح الباب المتكرر.\n" +
                "3- خلل بدائرة إذابة الثلج في أجهزة No Frost.\n\n" +
                "الفحص:\n" +
                "• تأكد من إغلاق الباب وسلامة الجوان.\n" +
                "• لا تستخدم سكينًا أو أداة حادة لإزالة الثلج.\n" +
                "• إذا عاد الثلج بسرعة في No Frost، يلزم فحص السخان والحساس والفيوز الحراري ودائرة التحكم بواسطة فني."));

        faults.add(new Fault("fridge",
                "مياه أسفل الثلاجة",
                "ثلاجة مياه صرف تصريف إذابة",
                "الأسباب المحتملة:\n" +
                "1- انسداد مجرى تصريف مياه الإذابة.\n" +
                "2- انسكاب مياه أو مشكلة في وعاء التجميع.\n" +
                "3- عدم استواء الجهاز أو تسريب من مصدر آخر.\n\n" +
                "الفحص:\n" +
                "• افصل الكهرباء إذا كانت المياه قريبة من القابس أو التوصيلات.\n" +
                "• افحص مصدر المياه الظاهر دون فك أجزاء كهربائية.\n" +
                "• إذا استمر التسريب، يحتاج الجهاز إلى فحص مجرى الصرف ووعاء التجميع."));

        faults.add(new Fault("electrical",
                "فحص مكثف التشغيل",
                "مكثف كباستور سعة ميكروفاراد",
                "تنبيه: المكثف قد يحتفظ بشحنة كهربائية حتى بعد فصل الجهاز.\n\n" +
                "المبدأ الفني:\n" +
                "• افحص بيانات السعة والجهد المكتوبة على المكثف.\n" +
                "• قياس السعة يحتاج جهاز قياس مناسبًا وبعد عزل المكثف وتفريغه بالطريقة المهنية الآمنة.\n" +
                "• تُقارن القراءة بالمواصفات وسماحية الشركة المصنعة.\n\n" +
                "لا تلمس أطراف المكثف ولا تقصرها بمفك. إذا لم تكن مدربًا على التعامل مع الكهرباء، اترك الفحص لفني مؤهل."));

        faults.add(new Fault("electrical",
                "القاطع يفصل عند التشغيل",
                "قاطع كهرباء ماس شورت حمل زائد",
                "الأسباب المحتملة:\n" +
                "1- حمل زائد أو قاطع غير مناسب.\n" +
                "2- قصر كهربائي أو تسريب تيار.\n" +
                "3- عطل في محرك أو ضاغط أو توصيلات.\n\n" +
                "الفحص:\n" +
                "• لا تكرر إعادة تشغيل القاطع إذا كان يفصل مباشرة.\n" +
                "• افصل الجهاز واطلب فحصًا كهربائيًا مؤهلًا.\n" +
                "• يجب قياس العزل والتيار وفحص التأريض والقاطع وفق المواصفات.\n\n" +
                "لا تركب قاطعًا أكبر ولا تتجاوز وسائل الحماية دون تصميم معتمد."));

        faults.add(new Fault("basics",
                "دورة التبريد الأساسية",
                "مبخر مكثف ضاغط صمام تمدد دورة",
                "تتكون دورة التبريد الانضغاطية الأساسية من أربعة أجزاء:\n\n" +
                "1- المبخر: يمتص الحرارة من الحيز المراد تبريده.\n\n" +
                "2- الضاغط (الكمبروسر): يرفع ضغط وسيط التبريد ودرجة حرارته.\n\n" +
                "3- المكثف: يطرد الحرارة إلى الوسط المحيط.\n\n" +
                "4- عنصر التمدد: يخفض الضغط وينظم تدفق وسيط التبريد.\n\n" +
                "ترتيب الدورة الشائع: المبخر ← الضاغط ← المكثف ← عنصر التمدد ← المبخر.\n\n" +
                "التشخيص الصحيح يعتمد على قياسات وظروف التشغيل ونوع وسيط التبريد، وليس على الضغط وحده."));
    }

    private void startScreen(String title, String subtitle) {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(18, 20, 18, 12);
        root.setBackgroundColor(bg);

        TextView logo = new TextView(this);
        logo.setText("MANDO  •  HVAC PRO");
        logo.setTextColor(cyan);
        logo.setTextSize(22);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        logo.setPadding(0, 4, 0, 12);
        root.addView(logo);

        TextView heading = new TextView(this);
        heading.setText(title);
        heading.setTextColor(white);
        heading.setTextSize(22);
        heading.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        heading.setGravity(Gravity.CENTER);
        heading.setPadding(0, 6, 0, 8);
        root.addView(heading);

        TextView sub = new TextView(this);
        sub.setText(subtitle);
        sub.setTextColor(muted);
        sub.setTextSize(14);
        sub.setGravity(Gravity.CENTER);
        sub.setPadding(0, 0, 0, 12);
        root.addView(sub);

        Button back = makeButton("↩ رجوع");
        back.setOnClickListener(v -> backAction.run());
        root.addView(back);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(false);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(0, 14, 0, 24);

        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

        setContentView(root);
    }

    private Button makeButton(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextColor(white);
        b.setTextSize(15);
        b.setAllCaps(false);
        b.setPadding(12, 10, 12, 10);

        GradientDrawable shape = new GradientDrawable();
        shape.setColor(panel);
        shape.setCornerRadius(22);
        shape.setStroke(2, cyan);
        b.setBackground(shape);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 0, 0, 12);
        b.setLayoutParams(p);
        return b;
    }

    private void addButton(String text, Runnable action) {
        Button b = makeButton(text);
        b.setOnClickListener(v -> action.run());
        content.addView(b);
    }

    private void addText(String text) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextColor(white);
        t.setTextSize(16);
        t.setLineSpacing(5, 1.1f);
        t.setPadding(14, 14, 14, 14);
        t.setBackgroundColor(panel);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 0, 0, 14);
        content.addView(t, p);
    }

    private void showHome() {
        backAction = this::showHome;
        startScreen("الصفحة الرئيسية",
                "تعلم التبريد والتكييف خطوة بخطوة");

        addButton("📘 أساسيات التبريد ودورة التبريد",
                () -> showFaultList("basics", "أساسيات التبريد", null,
                        this::showHome));

        addButton("❄️ أعطال التكييف",
                () -> showFaultList("ac", "أعطال التكييف", null,
                        this::showHome));

        addButton("🧊 أعطال الثلاجات والديب فريزر",
                () -> showFaultList("fridge", "أعطال الثلاجات", null,
                        this::showHome));

        addButton("⚡ الكهرباء ومكونات التشغيل",
                () -> showFaultList("electrical", "الكهرباء", null,
                        this::showHome));

        addButton("🏢 الشركات والأجهزة",
                this::showBrands);

        addButton("🔎 ابحث عن عطل أو درس",
                this::showSearch);

        addButton("📐 معلومات القياس والحسابات",
                this::showCalculations);

        addButton("❓ العطل غير موجود؟ تواصل مع المطوّر",
                this::showNotFound);
    }

    private void showBrands() {
        backAction = this::showHome;
        startScreen("الشركات والأجهزة",
                "اختر الشركة ثم نوع الجهاز");

        String[] brands = {
                "توشيبا Toshiba", "كريازي Kiriazi", "بيكو Beko",
                "يونيون إير Unionaire", "كاريير Carrier", "إل جي LG",
                "سامسونج Samsung", "ميديا Midea", "شارب Sharp",
                "دايكن Daikin", "يورك York", "فريش Fresh"
        };

        for (String brand : brands) {
            addButton("🏢 " + brand, () -> showDevices(brand));
        }

        addText("ملاحظة: اختيار الشركة يفتح دليل الأعطال العامة للجهاز. " +
                "أكواد الأعطال والتوصيلات الخاصة بكل موديل يجب مراجعتها " +
                "في دليل الخدمة الرسمي قبل اتخاذ قرار الصيانة.");
    }

    private void showDevices(String brand) {
        backAction = this::showBrands;
        startScreen(brand, "اختر نوع الجهاز");

        addText("الشركة المختارة: " + brand +
                "\nالمعلومات الحالية إرشادات عامة وليست قاعدة أكواد معتمدة لكل موديل.");

        addButton("تكييف سبليت / شباك / متنقل",
                () -> showFaultList("ac", "أعطال التكييف - " + brand,
                        brand, () -> showDevices(brand)));

        addButton("تكييف إنفرتر",
                () -> showFaultList("ac", "أعطال الإنفرتر - " + brand,
                        brand, () -> showDevices(brand)));

        addButton("ثلاجة / ديب فريزر",
                () -> showFaultList("fridge", "أعطال التبريد المنزلي - " + brand,
                        brand, () -> showDevices(brand)));

        addButton("أعطال الكهرباء ومكونات التشغيل",
                () -> showFaultList("electrical", "الكهرباء - " + brand,
                        brand, () -> showDevices(brand)));

        addButton("أكواد الأعطال الخاصة بالموديل",
                this::showNotFound);
    }

    private void showFaultList(String category, String title,
                               String brand, Runnable parent) {
        backAction = parent;
        startScreen(title, "اختر العطل لعرض خطوات الفحص");

        boolean found = false;
        for (Fault f : faults) {
            if (f.category.equals(category)) {
                found = true;
                addButton("🔧 " + f.title,
                        () -> showArticle(f, parent));
            }
        }

        if (!found) {
            addText("القسم ده لسه تحت التطوير.");
            addButton("📲 اطلب إضافة المحتوى على واتساب",
                    this::showNotFound);
        }

        addButton("❓ عطل آخر غير موجود",
                this::showNotFound);
    }

    private void showArticle(Fault fault, Runnable parent) {
        backAction = parent;
        startScreen(fault.title, "دليل فحص مبدئي");

        if (fault.category.equals("ac") ||
                fault.category.equals("fridge") ||
                fault.category.equals("electrical")) {
            addText("تنبيه: الأسباب التالية احتمالات للفحص وليست تشخيصًا مؤكدًا. " +
                    "القياسات ودليل صيانة الجهاز هما أساس تحديد العطل.");
        }

        addText(fault.details);

        addText("بعد الإصلاح:\n" +
                "• أعد تركيب الأغطية ووسائل الحماية.\n" +
                "• تأكد من عدم وجود تسريب أو صوت أو سخونة غير طبيعية.\n" +
                "• اختبر الجهاز في ظروف تشغيل مناسبة وسجّل القياسات.\n" +
                "• إذا استمر العطل، لا تستبدل قطعًا بالتخمين.");

        addButton("📲 أبلغ المطوّر عن نقص أو خطأ في المحتوى",
                this::showNotFound);
    }

    private void showSearch() {
        backAction = this::showHome;
        startScreen("البحث", "اكتب كلمة من اسم العطل أو الدرس");

        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setTextColor(white);
        input.setHintTextColor(muted);
        input.setHint("مثال: التكييف لا يبرد");
        input.setTextSize(16);
        input.setPadding(14, 10, 14, 10);
        input.setBackgroundColor(panel);
        content.addView(input);

        addButton("🔎 بحث", () -> {
            String query = input.getText().toString().trim()
                    .toLowerCase(Locale.ROOT);

            ((InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(input.getWindowToken(), 0);

            if (query.isEmpty()) {
                Toast.makeText(this, "اكتب اسم العطل أولًا",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            showSearchResults(query);
        });
    }

    private void showSearchResults(String query) {
        backAction = this::showSearch;
        startScreen("نتائج البحث", "نتائج مطابقة للكلمات التي كتبتها");

        int count = 0;
        for (Fault f : faults) {
            String all = (f.title + " " + f.keywords + " " + f.details)
                    .toLowerCase(Locale.ROOT);

            if (all.contains(query)) {
                count++;
                addButton("🔧 " + f.title,
                        () -> showArticle(f, () -> showSearchResults(query)));
            }
        }

        if (count == 0) {
            addText("ملقيناش نتيجة مطابقة. القسم ده ممكن يكون لسه تحت التطوير.");
            addButton("📲 اطلب إضافة العطل على واتساب",
                    this::showNotFound);
        }
    }

    private void showCalculations() {
        backAction = this::showHome;
        startScreen("القياس والحسابات", "معلومات أساسية للفني");

        addText("1- فرق درجة الحرارة:\n" +
                "فرق الحرارة = درجة حرارة الدخول − درجة حرارة الخروج.\n\n" +
                "لا يوجد رقم واحد مناسب لكل الأجهزة؛ قارن القياسات " +
                "بظروف التشغيل ومواصفات الشركة.");

        addText("2- قانون القدرة الكهربائية:\n" +
                "في دائرة تيار مستمر: القدرة بالوات = الجهد بالفولت × التيار بالأمبير.\n\n" +
                "في التيار المتردد، حساب القدرة الفعلية قد يحتاج معامل القدرة " +
                "وطريقة التوصيل.");

        addText("3- وحدات مهمة:\n" +
                "• الجهد: فولت (V)\n" +
                "• التيار: أمبير (A)\n" +
                "• المقاومة: أوم (Ω)\n" +
                "• القدرة: وات (W)\n" +
                "• سعة المكثف: ميكروفاراد (µF)\n\n" +
                "لا تستخدم هذه المعلومات للعمل على دائرة حية دون تدريب وأدوات حماية مناسبة.");
    }

    private void showNotFound() {
        backAction = this::showHome;
        startScreen("المحتوى غير متوفر حاليًا",
                "MANDO HVAC PRO");

        addText("العطل أو الموديل ده لسه تحت التطوير، والمطوّر هيضيف معلومات جديدة مع مراجعتها.\n\n" +
                "مش هنقدّم حلًا غير مؤكد على إنه صحيح. ابعت اسم الشركة، موديل الجهاز، " +
                "ووصف العطل أو كود الخطأ عشان نحدد المحتوى المطلوب.");

        addButton("📲 التواصل مع المطوّر على واتساب",
                () -> openWhatsApp(
                        "مرحبًا، أريد إضافة عطل أو موديل إلى MANDO HVAC PRO."));

        addButton("🏠 العودة للرئيسية", this::showHome);
    }

    private void openWhatsApp(String message) {
        String url = "https://wa.me/201120133028?text=" +
                Uri.encode(message);

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this,
                    "تعذر فتح الرابط. حاول فتح واتساب يدويًا.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (backAction != null) {
            backAction.run();
        } else {
            showHome();
        }
    }
}
