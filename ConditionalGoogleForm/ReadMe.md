# Smart Google Form with Conditional Logic

A simplified version of Google Forms with support for conditional question visibility. This backend system allows admins to define dynamic form templates and supports real-time visibility updates as users fill out the form.

---

## 📌 Features

### 1. 🛠️ Create Form Template (Admin Flow)

- Admin can create a form with multiple-choice questions.
- Each question can have **conditional visibility logic**, e.g.,  
  _"Show Q2 if Q1 = Option 1"_.

### 2. 👀 Update Visibility Status (User Flow)

- Based on **partial answers**, the backend returns the current visibility status for all questions.
- This helps the frontend dynamically show/hide questions.
- **Hidden question answers** are ignored during submission.

### 3. 📨 Submit Form (User Flow)

- Accept final form responses from the user.
- Automatically ignore any answers to hidden questions.
- Return a clean, validated list of accepted responses.

---

## 📘 Example Use Case

```json
Form:
  - Q1: "Do you own a car?" → Options: Yes, No
  - Q2: "What is the make of your car?" → Show if Q1 = Yes
  - Q3: "Do you have a driving license?" → Always visible

User Answers:
  - Q1: No
  - Q2: Toyota
  - Q3: Yes

Result after submission:
  - Q1: No
  - Q3: Yes
  (Q2 is ignored since it was hidden)
