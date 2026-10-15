
/* MANDO HVAC PRO - Local Admin Upgrade
   All changes are stored on this device. No server required. */
(function () {
  "use strict";

  const STORE = "mando_hvac_admin_upgrade_v1";
  const DEFAULT_PASSWORD = "MANDO2026";
  const DEFAULT_WA = "201120133028";

  function read() {
    try {
      return Object.assign({
        password: DEFAULT_PASSWORD,
        whatsapp: DEFAULT_WA,
        appTitle: "MANDO HVAC PRO",
        accent: "#00eaff",
        categories: ["أساسيات", "الكهرباء", "الثلاجات", "النوفروست", "الأعطال"],
        lessons: [],
        questions: []
      }, JSON.parse(localStorage.getItem(STORE) || "{}"));
    } catch (e) {
      return {
        password: DEFAULT_PASSWORD,
        whatsapp: DEFAULT_WA,
        appTitle: "MANDO HVAC PRO",
        accent: "#00eaff",
        categories: ["أساسيات", "الكهرباء", "الثلاجات", "النوفروست", "الأعطال"],
        lessons: [],
        questions: []
      };
    }
  }

  let cfg = read();
  const $ = id => document.getElementById(id);
  const esc = s => String(s ?? "").replace(/[&<>"']/g, c => ({
    "&":"&amp;", "<":"&lt;", ">":"&gt;", '"':"&quot;", "'":"&#39;"
  }[c]));

  function persist() {
    try {
      localStorage.setItem(STORE, JSON.stringify(cfg));
      return true;
    } catch (e) {
      alert("مساحة التخزين غير كافية. صدّر نسخة احتياطية أو قلّل حجم المحتوى.");
      return false;
    }
  }

  function addStyles() {
    const style = document.createElement("style");
    style.textContent = `
      .mando-admin-box{margin-top:14px;padding:14px;border:1px solid #236176;
        border-radius:14px;background:#0b1b2a}
      .mando-admin-box h3{color:var(--neon)}
      .mando-wa{position:fixed;left:14px;bottom:90px;z-index:20;
        background:#168b52;color:white;padding:12px 16px;border-radius:28px;
        font-weight:bold;text-decoration:none;box-shadow:0 4px 18px #0007}
      .mando-item{padding:10px;margin:8px 0;border:1px solid #285065;border-radius:10px}
    `;
    document.head.appendChild(style);
  }

  function addWhatsApp() {
    let link = $("mandoWhatsApp");
    if (!link) {
      link = document.createElement("a");
      link.id = "mandoWhatsApp";
      link.className = "mando-wa";
      link.target = "_blank";
      link.rel = "noopener";
      link.textContent = "واتساب 💬";
      document.body.appendChild(link);
    }
    link.href = "https://wa.me/" + cfg.whatsapp.replace(/\D/g, "");
  }

  function adminBox() {
    const panel = $("adminPanel");
    if (!panel || $("mandoUpgradePanel")) return;

    const box = document.createElement("div");
    box.id = "mandoUpgradePanel";
    box.className = "mando-admin-box";
    box.innerHTML = `
      <h3>🛠️ لوحة الإدارة الموسعة</h3>
      <p class="muted">التغييرات في هذا القسم تُحفظ محليًا على هذا الجهاز فقط.</p>

      <h4>⚙️ إعدادات التطبيق</h4>
      <label>اسم التطبيق</label>
      <input id="muTitle" maxlength="70">
      <label>لون الواجهة (HEX)</label>
      <input id="muColor" placeholder="#00eaff" maxlength="7">
      <label>رقم واتساب الدولي بدون +</label>
      <input id="muWhatsApp" inputmode="tel" placeholder="201120133028">
      <button class="btn primary" id="muSaveSettings">حفظ الإعدادات</button>

      <h4>🔐 تغيير كلمة مرور الأدمن</h4>
      <label>كلمة المرور الحالية</label>
      <input id="muOldPass" type="password" autocomplete="current-password">
      <label>كلمة المرور الجديدة (8 أحرف على الأقل)</label>
      <input id="muNewPass" type="password" autocomplete="new-password">
      <button class="btn" id="muChangePass">تغيير كلمة المرور</button>

      <h4>📚 إضافة درس</h4>
      <label>عنوان الدرس</label><input id="muLessonTitle">
      <label>القسم</label><input id="muLessonCategory" placeholder="مثال: كهرباء الثلاجات">
      <label>ملخص الدرس</label><textarea id="muLessonSummary" rows="2"></textarea>
      <label>شرح الدرس</label><textarea id="muLessonBody" rows="7"
        placeholder="اكتب الشرح والخطوات والتحذيرات بالتفصيل"></textarea>
      <button class="btn primary" id="muAddLesson">حفظ الدرس</button>
      <div id="muLessonsList"></div>

      <h4>🧠 إضافة سؤال اختيار من متعدد</h4>
      <label>السؤال</label><textarea id="muQuestion" rows="2"></textarea>
      <label>الاختيار أ</label><input id="muA">
      <label>الاختيار ب</label><input id="muB">
      <label>الاختيار ج</label><input id="muC">
      <label>الاختيار د</label><input id="muD">
      <label>رقم الإجابة الصحيحة</label>
      <select id="muCorrect">
        <option value="0">أ</option><option value="1">ب</option>
        <option value="2">ج</option><option value="3">د</option>
      </select>
      <label>شرح الإجابة</label><textarea id="muWhy" rows="2"></textarea>
      <button class="btn primary" id="muAddQuestion">حفظ السؤال</button>
      <div id="muQuestionsList"></div>

      <h4>🧊 إضافة دروس دوائر الثلاجات</h4>
      <button class="btn" id="muFridgeNormal">إضافة درس الثلاجة العادية</button>
      <button class="btn" id="muFridgeNoFrost">إضافة درس النوفروست</button>

      <div class="notice">
        تنبيه: لوحة الإدارة محلية وليست حماية مركزية. لا تستخدمها لتخزين أسرار
        حساسة. تغيير كلمة المرور هنا ليس بديلًا عن حماية التطبيق نفسه.
        افصل الكهرباء وتحقق من انعدام الجهد قبل أي فحص كهربائي.
      </div>
    `;
    panel.appendChild(box);

    $("muTitle").value = cfg.appTitle;
    $("muColor").value = cfg.accent;
    $("muWhatsApp").value = cfg.whatsapp;
    renderManagedLists();

    $("muSaveSettings").onclick = () => {
      const title = $("muTitle").value.trim();
      const color = $("muColor").value.trim();
      const wa = $("muWhatsApp").value.replace(/\D/g, "");
      if (!title) return alert("اكتب اسم التطبيق.");
      if (!/^#[0-9a-fA-F]{6}$/.test(color)) return alert("اكتب لونًا صحيحًا مثل #00eaff.");
      if (wa.length < 10 || wa.length > 15) return alert("راجع رقم واتساب الدولي.");
      cfg.appTitle = title;
      cfg.accent = color;
      cfg.whatsapp = wa;
      if (!persist()) return;
      document.documentElement.style.setProperty("--neon", color);
      document.title = title;
      const logo = document.querySelector(".logo");
      if (logo) logo.textContent = "✦ " + title + " ✦";
      addWhatsApp();
      alert("تم حفظ الإعدادات على هذا الجهاز.");
    };

    $("muChangePass").onclick = () => {
      if ($("muOldPass").value !== cfg.password)
        return alert("كلمة المرور الحالية غير صحيحة.");
      const next = $("muNewPass").value;
      if (next.length < 8)
        return alert("كلمة المرور الجديدة يجب ألا تقل عن 8 أحرف.");
      cfg.password = next;
      if (!persist()) return;
      $("muOldPass").value = "";
      $("muNewPass").value = "";
      alert("تم تغيير كلمة المرور المحلية.");
    };

    $("muAddLesson").onclick = () => {
      const title = $("muLessonTitle").value.trim();
      const category = $("muLessonCategory").value.trim() || "دروس إضافية";
      const summary = $("muLessonSummary").value.trim();
      const body = $("muLessonBody").value.trim();
      if (!title || !body) return alert("اكتب عنوان الدرس والشرح.");
      cfg.lessons.push({
        id: "mu_" + Date.now(),
        title, category, summary, body
      });
      if (!persist()) return;
      ["muLessonTitle","muLessonCategory","muLessonSummary","muLessonBody"]
        .forEach(id => $(id).value = "");
      renderManagedLists();
      alert("تم حفظ الدرس.");
    };

    $("muAddQuestion").onclick = () => {
      const q = $("muQuestion").value.trim();
      const answers = [$("muA").value.trim(), $("muB").value.trim(),
        $("muC").value.trim(), $("muD").value.trim()];
      if (!q || answers.some(a => !a))
        return alert("اكتب السؤال والاختيارات الأربعة.");
      cfg.questions.push({
        id: "mq_" + Date.now(),
        q, a: answers, correct: Number($("muCorrect").value),
        why: $("muWhy").value.trim()
      });
      if (!persist()) return;
      ["muQuestion","muA","muB","muC","muD","muWhy"]
        .forEach(id => $(id).value = "");
      renderManagedLists();
      alert("تم حفظ السؤال في بنك الأسئلة المحلي.");
    };

    $("muFridgeNormal").onclick = () => addFridgeLesson(false);
    $("muFridgeNoFrost").onclick = () => addFridgeLesson(true);
  }

  function addFridgeLesson(noFrost) {
    const title = noFrost ? "دائرة الثلاجة النوفروست" : "دائرة الثلاجة العادية";
    if (cfg.lessons.some(l => l.title === title))
      return alert("الدرس موجود بالفعل في قائمة الدروس الإضافية.");

    const body = noFrost
      ? `المكونات المحتملة حسب التصميم: الضاغط، المكثف، وسيلة التمدد،
المبخر، مروحة المبخر، سخان إذابة الثلج، حساس أو ثرموستات الإذابة،
مؤقت أو لوحة تحكم، ومفاتيح الأمان.

مسار التبريد: الضاغط ← المكثف ← الفلتر المجفف والكابلري غالبًا
← المبخر ← خط السحب إلى الضاغط.

دورة إزالة الثلج في كثير من التصاميم تتحكم فيها لوحة أو مؤقت،
وتشمل السخان وحساس الإنهاء ووسيلة حماية حرارية عند وجودها.
المخطط الفعلي يختلف بين الموديلات؛ لا تفترض أن جميع الأطراف متشابهة.

افصل القابس قبل الفحص. لا توصل السخان أو الضاغط مباشرة للتجربة،
ولا تتجاوز الحساسات أو وسائل الحماية. ارجع إلى مخطط الموديل
واستخدم فنيًا مؤهلًا عند فحص دوائر جهد الشبكة.`
      : `المكونات المحتملة: الضاغط، جهاز بدء مناسب لنوع الضاغط
(مثل PTC أو ريلاي حسب التصميم)، واقي زيادة الحمل، الثرموستات،
والمصباح ومفتاح الباب في بعض الأجهزة.

مسار التبريد: الضاغط ← المكثف ← الفلتر المجفف ← الكابلري
← المبخر ← خط السحب إلى الضاغط.

دائرة التحكم في كثير من الثلاجات التقليدية تمر عبر الثرموستات
ووسائل الحماية وجهاز بدء الضاغط، لكن ترتيب التوصيل وأطرافه
يختلفان حسب الشركة والموديل ونوع الضاغط.

لا تعتمد على ألوان الأسلاك وحدها، ولا تستخدم رسمًا عامًا بدل
مخطط الجهاز. افصل القابس وتحقق من انعدام الجهد قبل الفحص.
المكثفات قد تحتفظ بشحنة. لا تجسّر الحمايات ولا توصل أطراف
الضاغط عشوائيًا؛ أعمال 220 فولت لفني مؤهل فقط.`;

    cfg.lessons.push({
      id: "fridge_" + Date.now(),
      category: "كهرباء الثلاجات",
      title,
      summary: "شرح تعليمي عام؛ راجع مخطط الشركة والموديل قبل الصيانة.",
      body
    });
    if (!persist()) return;
    renderManagedLists();
    alert("أُضيف الدرس إلى قائمة الدروس الإضافية.");
  }

  function renderManagedLists() {
    const lessonsList = $("muLessonsList");
    const questionsList = $("muQuestionsList");
    if (lessonsList) {
      lessonsList.innerHTML = "<h4>الدروس المضافة من لوحة الإدارة</h4>" +
        (cfg.lessons.map(l => `
          <div class="mando-item">
            <b>${esc(l.title)}</b><p class="muted">${esc(l.category)}</p>
            <button class="btn danger small" data-del-lesson="${esc(l.id)}">حذف</button>
          </div>`).join("") || '<p class="muted">لا توجد إضافات بعد.</p>');
      lessonsList.querySelectorAll("[data-del-lesson]").forEach(btn => {
        btn.onclick = () => {
          if (!confirm("حذف هذا الدرس الإضافي؟")) return;
          cfg.lessons = cfg.lessons.filter(l => l.id !== btn.dataset.delLesson);
          if (persist()) renderManagedLists();
        };
      });
    }
    if (questionsList) {
      questionsList.innerHTML = "<h4>الأسئلة المضافة من لوحة الإدارة</h4>" +
        (cfg.questions.map(q => `
          <div class="mando-item">
            <b>${esc(q.q)}</b><p class="muted">${esc(q.a[q.correct])}</p>
            <button class="btn danger small" data-del-question="${esc(q.id)}">حذف</button>
          </div>`).join("") || '<p class="muted">لا توجد أسئلة إضافية بعد.</p>');
      questionsList.querySelectorAll("[data-del-question]").forEach(btn => {
        btn.onclick = () => {
          if (!confirm("حذف هذا السؤال الإضافي؟")) return;
          cfg.questions = cfg.questions.filter(q => q.id !== btn.dataset.delQuestion);
          if (persist()) renderManagedLists();
        };
      });
    }
  }

  // Connect the new local password to the existing admin login.
  const originalLogin = window.adminLogin;
  window.adminLogin = function () {
    const pass = $("adminPass");
    if (pass && pass.value === cfg.password) {
      $("adminLoginBox").classList.add("hidden");
      $("adminPanel").classList.remove("hidden");
      adminBox();
      return;
    }
    if (originalLogin) originalLogin();
  };

  // Merge admin-created lessons into the existing lesson library.
  const originalAllLessons = window.allLessons;
  window.allLessons = function () {
    const base = originalAllLessons ? originalAllLessons() : [];
    return base.concat(cfg.lessons);
  };

  // Make admin-created questions available to the existing quiz.
  const originalStartQuiz = window.startQuiz;
  window.startQuiz = function () {
    if (originalStartQuiz) originalStartQuiz();
    // Existing quiz logic is preserved; added questions are stored separately
    // until the quiz engine is explicitly extended to render them.
  };

  function boot() {
    addStyles();
    addWhatsApp();
    document.documentElement.style.setProperty("--neon", cfg.accent);
    document.title = cfg.appTitle;
    const logo = document.querySelector(".logo");
    if (logo) logo.textContent = "✦ " + cfg.appTitle + " ✦";
  }

  if (document.readyState === "loading")
    document.addEventListener("DOMContentLoaded", boot);
  else boot();
})();
