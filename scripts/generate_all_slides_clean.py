import os
from PIL import Image, ImageDraw, ImageFont, ImageFilter, JpegImagePlugin

WIDTH = 1080
HEIGHT = 1350

FONT_BOLD_PATH = "/System/Library/Fonts/Supplemental/Arial Bold.ttf"
FONT_REG_PATH = "/System/Library/Fonts/Supplemental/Arial.ttf"

# Professional Tech Color Palette (Clean, Minimal, No Thorana)
BG_COLOR_TOP = (11, 17, 32)       # Deep slate navy
BG_COLOR_BOTTOM = (15, 23, 42)    # Clean slate dark
TEXT_WHITE = (248, 250, 252)      # Pure clean white
TEXT_MUTED = (148, 163, 184)      # Cool muted slate
TEXT_DIM = (100, 116, 139)        # Subtle dark slate
ACCENT_BLUE = (96, 165, 250)      # Tech Cyan / Blue
ACCENT_BLUE_DEEP = (37, 99, 235)  # Royal Blue
CARD_BG = (19, 28, 48)            # Dark slate card
CARD_BORDER = (40, 53, 80)        # Subtle 1px border

def get_font(path, size):
    try:
        return ImageFont.truetype(path, size)
    except:
        return ImageFont.load_default()

def create_base_canvas(slide_num, total_slides, tag_text):
    base = Image.new('RGB', (WIDTH, HEIGHT), BG_COLOR_TOP)
    draw = ImageDraw.Draw(base)
    
    # Subtle clean vertical gradient
    r1, g1, b1 = BG_COLOR_TOP
    r2, g2, b2 = BG_COLOR_BOTTOM
    for y in range(HEIGHT):
        ratio = y / float(HEIGHT)
        r = int(r1 * (1 - ratio) + r2 * ratio)
        g = int(g1 * (1 - ratio) + g2 * ratio)
        b = int(b1 * (1 - ratio) + b2 * ratio)
        draw.line([(0, y), (WIDTH, y)], fill=(r, g, b))
        
    # Top header bar (Clean & minimalist)
    font_tag = get_font(FONT_BOLD_PATH, 18)
    font_page = get_font(FONT_BOLD_PATH, 20)
    
    # Tag Pill
    tag_bbox = draw.textbbox((0, 0), tag_text, font=font_tag)
    pill_w = tag_bbox[2] - tag_bbox[0] + 32
    draw.rounded_rectangle([(70, 50), (70 + pill_w, 86)], radius=18, fill=(24, 34, 56), outline=CARD_BORDER, width=1)
    draw.text((86, 58), tag_text, font=font_tag, fill=ACCENT_BLUE)
    
    # Page indicator
    page_str = f"{slide_num:02d} / {total_slides:02d}"
    draw.text((WIDTH - 145, 58), page_str, font=font_page, fill=TEXT_MUTED)
    
    # Hairline divider under header
    draw.line([(70, 104), (WIDTH - 70, 104)], fill=(28, 39, 62), width=1)
    
    # Bottom footer line
    draw.line([(70, HEIGHT - 75), (WIDTH - 70, HEIGHT - 75)], fill=(28, 39, 62), width=1)
    font_foot = get_font(FONT_REG_PATH, 18)
    font_foot_b = get_font(FONT_BOLD_PATH, 18)
    draw.text((70, HEIGHT - 55), "LibroCore — Engineering Case Study", font=font_foot, fill=TEXT_DIM)
    draw.text((WIDTH - 340, HEIGHT - 55), "github.com/theekshana-sudeepa", font=font_foot, fill=TEXT_MUTED)
    
    return base, draw

def add_rounded_corners(im, rad):
    mask = Image.new('L', im.size, 0)
    draw = ImageDraw.Draw(mask)
    draw.rounded_rectangle([(0, 0), im.size], rad, fill=255)
    result = im.copy()
    result.putalpha(mask)
    return result

def add_shadow(im, offset=(0, 12), blur=20, shadow_color=(0, 0, 0, 180)):
    total_w = im.size[0] + blur * 2 + abs(offset[0])
    total_h = im.size[1] + blur * 2 + abs(offset[1])
    shadow_img = Image.new('RGBA', (total_w, total_h), (0, 0, 0, 0))
    sdraw = ImageDraw.Draw(shadow_img)
    sx = blur + max(0, offset[0])
    sy = blur + max(0, offset[1])
    sdraw.rounded_rectangle([(sx, sy), (sx + im.size[0], sy + im.size[1])], radius=14, fill=shadow_color)
    shadow_img = shadow_img.filter(ImageFilter.GaussianBlur(blur))
    shadow_img.paste(im, (blur, blur), im)
    return shadow_img

def make_macos_window(content_img, title="LibroCore Desktop Client", target_w=940):
    # Proportional scaling (No trimming, no squishing)
    ratio = target_w / float(content_img.size[0])
    target_h = int(content_img.size[1] * ratio)
    resized_content = content_img.resize((target_w, target_h), Image.LANCZOS)
    
    titlebar_h = 36
    total_w = target_w
    total_h = target_h + titlebar_h
    
    frame = Image.new('RGBA', (total_w, total_h), (19, 28, 48, 255))
    draw = ImageDraw.Draw(frame)
    
    # Titlebar background
    draw.rectangle([(0, 0), (total_w, titlebar_h)], fill=(24, 34, 56))
    # Border around entire window
    draw.rectangle([(0, 0), (total_w - 1, total_h - 1)], outline=(51, 65, 85), width=1)
    # Divider between titlebar and content
    draw.line([(0, titlebar_h), (total_w, titlebar_h)], fill=(51, 65, 85), width=1)
    
    # 3 traffic light dots
    draw.ellipse([14, 12, 24, 22], fill=(239, 68, 68))  # Red
    draw.ellipse([32, 12, 42, 22], fill=(245, 158, 11)) # Yellow
    draw.ellipse([50, 12, 60, 22], fill=(34, 197, 94))  # Green
    
    # Title text
    font = get_font(FONT_BOLD_PATH, 14)
    tb = draw.textbbox((0, 0), title, font=font)
    tw = tb[2] - tb[0]
    draw.text(((total_w - tw)//2, 10), title, font=font, fill=(148, 163, 184))
    
    # Paste content uncropped
    frame.paste(resized_content, (0, titlebar_h))
    
    # Add rounded corners & drop shadow
    frame_rounded = add_rounded_corners(frame, 12)
    return add_shadow(frame_rounded, offset=(0, 10), blur=18)

# ==================== SLIDE 1: CLEAN HERO COVER ====================
def make_slide1():
    base, draw = create_base_canvas(1, 6, "ACADEMIC ARCHIVE • SAD VIVA SUBMISSION")
    
    font_hero_tag = get_font(FONT_BOLD_PATH, 26)
    font_hero_title = get_font(FONT_BOLD_PATH, 48)
    font_hero_sub = get_font(FONT_REG_PATH, 24)
    font_card_head = get_font(FONT_BOLD_PATH, 22)
    font_card_sub = get_font(FONT_REG_PATH, 18)
    font_cta = get_font(FONT_BOLD_PATH, 22)
    
    y = 130
    draw.text((70, y), "BEFORE AI BOILERPLATE:", font=font_hero_tag, fill=ACCENT_BLUE)
    y += 42
    draw.text((70, y), "How We Hand-Coded a Full", font=font_hero_title, fill=TEXT_WHITE)
    y += 58
    draw.text((70, y), "Library Management System", font=font_hero_title, fill=ACCENT_BLUE)
    y += 58
    draw.text((70, y), "in NetBeans Java", font=font_hero_title, fill=TEXT_WHITE)
    y += 68
    
    sub1 = "University SAD Capstone • 208-Page System Architecture Spec"
    sub2 = "Handcrafted by 3 Student Engineers • Defended at Final Viva"
    draw.text((70, y), sub1, font=font_hero_sub, fill=TEXT_MUTED)
    y += 32
    draw.text((70, y), sub2, font=font_hero_sub, fill=TEXT_DIM)
    y += 45
    
    # Main Dashboard Window (Proportional, Uncropped)
    dash_path = "assets/extracted/superadmin_page107-000.png"
    signin_path = "assets/extracted/signin_page18-002.png"
    
    if os.path.exists(dash_path):
        dash = Image.open(dash_path)
        # 880w -> natural h ~ 560
        dash_win = make_macos_window(dash, title="LibroCore — SuperAdmin Analytics Dashboard", target_w=880)
        base.paste(dash_win, (70 - 18, y - 10), dash_win)
        
    # Floating Signin Window on bottom-right corner
    y_login = y + 250
    if os.path.exists(signin_path):
        signin = Image.open(signin_path)
        signin_win = make_macos_window(signin, title="LibroCore — Secure Access Portal", target_w=430)
        base.paste(signin_win, (WIDTH - 430 - 70 - 18, y_login), signin_win)
        
    # Bottom Metric Cards (Uniform slate/blue palette, NO thorana rainbow!)
    y_cards = 1035
    card_w = 215
    card_h = 115
    cards_data = [
        ("LibroCore", "Enterprise LMS"),
        ("15 Tables", "Normalized 3NF DB"),
        ("208 Pages", "Technical SAD Spec"),
        ("Zero AI", "100% Hand-Coded")
    ]
    
    start_x = 70
    gap = 26
    for i, (title, subtitle) in enumerate(cards_data):
        cx = start_x + i * (card_w + gap)
        draw.rounded_rectangle([(cx, y_cards), (cx + card_w, y_cards + card_h)], radius=12, fill=CARD_BG, outline=CARD_BORDER, width=1)
        # Subtle clean blue top bar
        draw.rounded_rectangle([(cx + 14, y_cards + 6), (cx + 46, y_cards + 9)], radius=2, fill=ACCENT_BLUE)
        draw.text((cx + 14, y_cards + 22), title, font=font_card_head, fill=TEXT_WHITE)
        draw.text((cx + 14, y_cards + 56), subtitle, font=font_card_sub, fill=TEXT_MUTED)

    # Bottom CTA Prompt
    y_footer = 1180
    draw.rounded_rectangle([(70, y_footer), (WIDTH - 70, y_footer + 58)], radius=12, fill=(24, 34, 56), outline=CARD_BORDER, width=1)
    cta_text = "Swipe to explore System Architecture, Database & UI Showcase  >"
    tb = draw.textbbox((0, 0), cta_text, font=font_cta)
    cta_w = tb[2] - tb[0]
    draw.text(((WIDTH - cta_w)//2, y_footer + 16), cta_text, font=font_cta, fill=TEXT_WHITE)
    
    base.save("assets/linkedin_cover_hero.png", quality=95)
    base.save("assets/slide1_cover.png", quality=95)
    print("Generated Slide 1 (Clean Hero Cover)")
    return base

# ==================== SLIDE 2: CASE STUDY CONTEXT ====================
def make_slide2():
    base, draw = create_base_canvas(2, 6, "CASE STUDY & ARCHITECTURAL SCOPE")
    font_h1 = get_font(FONT_BOLD_PATH, 42)
    font_lead = get_font(FONT_REG_PATH, 24)
    font_card_t = get_font(FONT_BOLD_PATH, 22)
    font_card_d = get_font(FONT_REG_PATH, 19)
    font_team_h = get_font(FONT_BOLD_PATH, 18)
    font_team_d = get_font(FONT_REG_PATH, 20)
    
    y = 125
    draw.text((70, y), "Real-World Engineering Case Study:", font=font_h1, fill=TEXT_WHITE)
    y += 52
    draw.text((70, y), "Automated Library Management System", font=font_h1, fill=ACCENT_BLUE)
    y += 62
    
    lead_txt = ("For our Software Application Development (SAD) university viva submission,\n"
                "our 3-member team was tasked with replacing manual paper register workflows\n"
                "with an automated, production-grade desktop management system.")
    draw.text((70, y), lead_txt, font=font_lead, fill=TEXT_MUTED, spacing=6)
    y += 105
    
    # 3 Clean Uniform Cards (No rainbow lines!)
    cards = [
        ("The Core Problem", "Manual paper ledger searches cause physical book bottlenecks, untracked overdue fines, and zero digital audit trails for public resource loans."),
        ("The Engineered Solution", "A high-performance Java desktop system with dual-tier RBAC (SuperAdmin & Admin), dynamic Barcode4J generation, and automated JasperReports PDF receipts."),
        ("The Academic Viva Defense", "Defended live in front of university examiners alongside a 208-page IEEE-standard technical specification covering all 4 architectural layers.")
    ]
    
    for title, desc in cards:
        draw.rounded_rectangle([(70, y), (WIDTH - 70, y + 135)], radius=14, fill=CARD_BG, outline=CARD_BORDER, width=1)
        # Clean subtle blue accent bar
        draw.rounded_rectangle([(70, y), (78, y + 135)], radius=3, fill=ACCENT_BLUE)
        draw.text((105, y + 18), title, font=font_card_t, fill=TEXT_WHITE)
        # wrap desc
        lines = []
        words = desc.split()
        cur = ""
        for w in words:
            if len(cur + " " + w) > 65:
                lines.append(cur)
                cur = w
            else:
                cur = cur + " " + w if cur else w
        if cur: lines.append(cur)
        draw.text((105, y + 54), "\n".join(lines), font=font_card_d, fill=TEXT_MUTED, spacing=5)
        y += 155
        
    y += 10
    # Team Box (Clean & Professional — Privacy-Safe Role Breakdown)
    draw.rounded_rectangle([(70, y), (WIDTH - 70, y + 185)], radius=14, fill=(19, 28, 48), outline=CARD_BORDER, width=1)
    draw.text((95, y + 20), "ENGINEERING ARCHITECTURE & OWNERSHIP", font=font_team_h, fill=ACCENT_BLUE)
    draw.text((95, y + 58), "• System Architecture & Core Logic — Theekshana Sudeepa", font=font_team_d, fill=TEXT_WHITE)
    draw.text((95, y + 96), "• Relational Modeling & Reporting — 3NF MySQL & JasperReports", font=font_team_d, fill=TEXT_WHITE)
    draw.text((95, y + 134), "• Desktop UI & Runtime Safety — FlatLaf Swing & EDT Timers", font=font_team_d, fill=TEXT_WHITE)
    
    base.save("assets/slide2_context.png", quality=95)
    print("Generated Slide 2")
    return base

# ==================== SLIDE 3: DATABASE ARCHITECTURE ====================
def make_slide3():
    base, draw = create_base_canvas(3, 6, "DATA ENGINEERING & 3NF SCHEMA")
    font_h1 = get_font(FONT_BOLD_PATH, 42)
    font_lead = get_font(FONT_REG_PATH, 24)
    font_pill_title = get_font(FONT_BOLD_PATH, 20)
    font_pill_desc = get_font(FONT_REG_PATH, 17)
    
    y = 125
    draw.text((70, y), "Normalized Relational Design:", font=font_h1, fill=TEXT_WHITE)
    y += 52
    draw.text((70, y), "15 Tables Across 4 System Layers", font=font_h1, fill=ACCENT_BLUE)
    y += 62
    
    lead_txt = ("Before writing a single line of Swing code, we designed a strict 3NF schema\n"
                "to ensure referential integrity, foreign-key cascades, and zero update anomalies.")
    draw.text((70, y), lead_txt, font=font_lead, fill=TEXT_MUTED, spacing=6)
    y += 85
    
    # ER Diagram (Proportional, Uncropped)
    er_path = "assets/extracted/er_page5-002.png"
    if os.path.exists(er_path):
        er = Image.open(er_path)
        er_win = make_macos_window(er, title="LibroCore — Normalized 3NF Entity Relationship Model", target_w=760)
        ex = (WIDTH - er_win.size[0]) // 2
        base.paste(er_win, (ex, y - 10), er_win)
        y += er_win.size[1] + 5
    else:
        y += 625
    # 4 Architecture Pillars
    pillars = [
        ("User & Privileges Layer", "SuperAdmin, Admin, Members, status lookup & credentials"),
        ("Book Classification Layer", "Hierarchical categories, subcategories, inventory & barcodes"),
        ("Circulation & Loans Layer", "Issue transactions, multi-day loans, dynamic fine calculation"),
        ("Audit Trail & Reports Layer", "System event logs, JasperReports exports & user feedback")
    ]
    
    card_w = 455
    card_h = 95
    positions = [(70, y), (WIDTH - 70 - card_w, y), (70, y + 110), (WIDTH - 70 - card_w, y + 110)]
    
    for (title, desc), (px, py) in zip(pillars, positions):
        draw.rounded_rectangle([(px, py), (px + card_w, py + card_h)], radius=12, fill=CARD_BG, outline=CARD_BORDER, width=1)
        draw.text((px + 16, py + 14), title, font=font_pill_title, fill=ACCENT_BLUE)
        lines = []
        words = desc.split()
        cur = ""
        for w in words:
            if len(cur + " " + w) > 34:
                lines.append(cur)
                cur = w
            else:
                cur = cur + " " + w if cur else w
        if cur: lines.append(cur)
        draw.text((px + 16, py + 42), "\n".join(lines), font=font_pill_desc, fill=TEXT_MUTED, spacing=4)
        
    base.save("assets/slide3_database.png", quality=95)
    print("Generated Slide 3")
    return base

# ==================== SLIDE 4: HAND-CRAFTED UI ====================
def make_slide4():
    base, draw = create_base_canvas(4, 6, "JAVA SWING CRAFTSMANSHIP")
    font_h1 = get_font(FONT_BOLD_PATH, 42)
    font_lead = get_font(FONT_REG_PATH, 24)
    font_card_t = get_font(FONT_BOLD_PATH, 22)
    font_card_d = get_font(FONT_REG_PATH, 18)
    
    y = 125
    draw.text((70, y), "Hand-Crafted Desktop UI:", font=font_h1, fill=TEXT_WHITE)
    y += 52
    draw.text((70, y), "NetBeans Swing Without AI Tools", font=font_h1, fill=ACCENT_BLUE)
    y += 62
    
    lead_txt = ("Every panel, custom table model, mouse event listener, and background\n"
                "gradient was hand-coded. No AI assistants, no auto-generators.")
    draw.text((70, y), lead_txt, font=font_lead, fill=TEXT_MUTED, spacing=6)
    y += 85
    
    # Book Management Screenshot (100% Uncropped, Proportional)
    book_p = "assets/extracted/adminbook_page55-000.png"
    if os.path.exists(book_p):
        book_img = Image.open(book_p)
        # width 940 -> height ~ 606 (100% uncropped!)
        book_win = make_macos_window(book_img, title="LibroCore — Book Inventory & Classification [Admin Mode]", target_w=940)
        base.paste(book_win, (70 - 18, y - 10), book_win)
        
    y += 665
    # 2 Technical Features (Clean, Uniform)
    feats = [
        ("Dynamic Search Filtering", "Instant keystroke search across ISBN, Title, Author & Dewey category using custom TableRowSorter models."),
        ("Modern Java2D Components", "Custom anti-aliased Canvas components (RoundButton, RoundTextfield) with smooth EDT execution.")
    ]
    for ft, fd in feats:
        draw.rounded_rectangle([(70, y), (WIDTH - 70, y + 80)], radius=12, fill=CARD_BG, outline=CARD_BORDER, width=1)
        draw.text((95, y + 14), ft, font=font_card_t, fill=ACCENT_BLUE)
        draw.text((95, y + 46), fd, font=font_card_d, fill=TEXT_MUTED)
        y += 95
        
    base.save("assets/slide4_ui_showcase.png", quality=95)
    print("Generated Slide 4")
    return base

# ==================== SLIDE 5: CIRCULATION DESK (UNCROPPED) ====================
def make_slide5():
    base, draw = create_base_canvas(5, 6, "CIRCULATION & TRANSACTION ENGINE")
    font_h1 = get_font(FONT_BOLD_PATH, 42)
    font_lead = get_font(FONT_REG_PATH, 24)
    font_card_t = get_font(FONT_BOLD_PATH, 22)
    font_card_d = get_font(FONT_REG_PATH, 18)
    
    y = 125
    draw.text((70, y), "Circulation & Loan Lifecycle:", font=font_h1, fill=TEXT_WHITE)
    y += 52
    draw.text((70, y), "Automated Checkouts & Due Tracking", font=font_h1, fill=ACCENT_BLUE)
    y += 62
    
    lead_txt = ("Eliminating manual book loss through real-time patron verification, barcode\n"
                "scanning, and automated loan transaction auditing.")
    draw.text((70, y), lead_txt, font=font_lead, fill=TEXT_MUTED, spacing=6)
    y += 85
    
    # Issue Desk Screenshot (100% Uncropped, Proportional!)
    issue_p = "assets/extracted/issue_page70-000.png"
    if os.path.exists(issue_p):
        issue_img = Image.open(issue_p)
        # width 940 -> natural height ~ 610 (No cropping, no squishing!)
        issue_win = make_macos_window(issue_img, title="LibroCore — Circulation Desk [Book Issue Operations]", target_w=940)
        base.paste(issue_win, (70 - 18, y - 10), issue_win)
        
    y += 665
    # 2 Feature highlights underneath
    feats = [
        ("Dual-Entity Validation", "Real-time verification linking Member NIC with physical Book Barcodes to validate borrowing limits."),
        ("Automated Due-Date Engine", "Calculates loan return windows, flags overdue accounts, and logs circulation receipts automatically.")
    ]
    for ft, fd in feats:
        draw.rounded_rectangle([(70, y), (WIDTH - 70, y + 80)], radius=12, fill=CARD_BG, outline=CARD_BORDER, width=1)
        draw.text((95, y + 14), ft, font=font_card_t, fill=ACCENT_BLUE)
        draw.text((95, y + 46), fd, font=font_card_d, fill=TEXT_MUTED)
        y += 95
        
    base.save("assets/slide5_automation.png", quality=95)
    print("Generated Slide 5 (Uncropped Circulation Desk)")
    return base

# ==================== SLIDE 6: RETURN DESK & RETROSPECTIVE (UNCROPPED) ====================
def make_slide6():
    base, draw = create_base_canvas(6, 6, "ENTERPRISE AUTOMATION & TAKEAWAYS")
    font_h1 = get_font(FONT_BOLD_PATH, 42)
    font_lead = get_font(FONT_REG_PATH, 24)
    font_card_t = get_font(FONT_BOLD_PATH, 22)
    font_card_d = get_font(FONT_REG_PATH, 18)
    
    y = 125
    draw.text((70, y), "Return Desk & Retrospective:", font=font_h1, fill=TEXT_WHITE)
    y += 52
    draw.text((70, y), "JasperReports, Barcodes & Lessons", font=font_h1, fill=ACCENT_BLUE)
    y += 62
    
    lead_txt = ("Enterprise library automation paired with first-principles engineering takeaways\n"
                "from our Software Application Development (SAD) oral viva defense.")
    draw.text((70, y), lead_txt, font=font_lead, fill=TEXT_MUTED, spacing=6)
    y += 85
    
    # Return Desk Screenshot (100% Uncropped, Proportional!)
    return_p = "assets/extracted/return_page88-000.png"
    if os.path.exists(return_p):
        return_img = Image.open(return_p)
        # width 940 -> height ~ 604 (100% uncropped!)
        return_win = make_macos_window(return_img, title="LibroCore — Return Desk & Overdue Penalty Processing", target_w=940)
        base.paste(return_win, (70 - 18, y - 10), return_win)
        
    y += 665
    # 2 Clean, high-credibility takeaway cards
    takeaways = [
        ("Barcode4J & JasperReports", "Integrated Barcode4J/ZXing for patron ID barcode printing and JasperReports for PDF circulation receipts."),
        ("Why Hand-Coding Matters", "Building JDBC managers, Swing EDT loops, and 3NF schemas from scratch builds deep systems intuition.")
    ]
    for title, desc in takeaways:
        draw.rounded_rectangle([(70, y), (WIDTH - 70, y + 80)], radius=12, fill=CARD_BG, outline=CARD_BORDER, width=1)
        draw.text((95, y + 14), title, font=font_card_t, fill=ACCENT_BLUE)
        draw.text((95, y + 46), desc, font=font_card_d, fill=TEXT_MUTED)
        y += 95
        
    base.save("assets/slide6_takeaways.png", quality=95)
    print("Generated Slide 6 (Uncropped Return Desk & Retrospective)")
    return base

print("Building all clean slides...")
s1 = make_slide1()
s2 = make_slide2()
s3 = make_slide3()
s4 = make_slide4()
s5 = make_slide5()
s6 = make_slide6()

pdf_path = "assets/LibroCore_LMS_LinkedIn_Carousel.pdf"
s1.save(pdf_path, save_all=True, append_images=[s2, s3, s4, s5, s6], resolution=150)
print(f"Successfully generated clean carousel PDF: {pdf_path}")
