package com.example.sweepstakeapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// The text block is defined once as a constant.
private const val termsAndConditionsContent = """1. Introduction

1.1. These Terms and Conditions (“Terms”) govern your access to and use of the Sweepstake App (“the App”), operated by SREGF Enterprises (“the Operator,” “we,” “us,” or “our”).
1.2. By downloading, accessing, or using the App, you (“the User,” “you,” or “your”) agree to be bound by these Terms and our Privacy Policy.
1.3. If you do not agree with these Terms, you must not use or access the App.
1.4. The Operator reserves the right to update, modify, or replace any part of these Terms at any time. Continued use of the App following such changes constitutes acceptance of the revised Terms.

2. Definitions

For the purposes of these Terms:

“App” means the Sweepstake App developed and operated by SREGF Enterprises.

“Services” means the sweepstakes, promotions, and related features provided via the App.

“Sweepstake” means any prize draw, contest, or promotional event available through the App.

“User” means any person who registers for, accesses, or participates in the App or any sweepstake.

“Prize” means any reward, benefit, or item awarded to a User following a valid sweepstake draw.

“Personal Data” refers to information that can identify an individual, in accordance with the UK General Data Protection Regulation (“UK GDPR”).

3. Eligibility and Age Verification

3.1. Participation in the App and its sweepstakes is restricted to individuals aged 18 years or older.
3.2. By creating an account, you confirm that you are at least 18 years of age and legally capable of entering binding agreements.
3.3. The Operator reserves the right to require proof of age or identity at any time.
3.4. Accounts found to be created by individuals under 18 will be terminated immediately, and any entries or prizes forfeited.
3.5. Users must not access or use the App from jurisdictions where participation is unlawful.

4. Permitted and Excluded Regions

4.1. The App is open to Users lawfully resident in the following jurisdictions:
United Kingdom, Australia, New Zealand, United States of America, Canada, India, Japan, South Korea, Singapore, and all member countries of the European Economic Area (EEA).
4.2. The Operator reserves the right to extend or limit access to additional regions at its discretion.
4.3. Participation is expressly prohibited in the following jurisdictions:
China, North Korea, and any other country where sweepstakes of this nature are restricted by law.
4.4. Users are responsible for ensuring that participation in sweepstakes is lawful in their local jurisdiction. The Operator accepts no liability for use in restricted jurisdictions.

5. Nature of the Sweepstake

5.1. The App operates legitimate sweepstakes, not a lottery or form of gambling.
5.2. Entry into sweepstakes does not require payment, purchase, or wagering.
5.3. No purchase or payment increases the chances of winning.
5.4. All sweepstakes are conducted fairly and transparently using randomised methods.
5.5. The Operator does not guarantee any particular prize outcome or winning probability.

6. Account Registration and User Responsibilities

6.1. To participate in sweepstakes, Users must register an account within the App.
6.2. Users agree to provide accurate, complete, and up-to-date information during registration.
6.3. Users are responsible for maintaining the confidentiality of their login credentials and for all activities under their account.
6.4. The Operator reserves the right to suspend or terminate accounts that provide false information or breach these Terms.

7. Entry, Draws, and Prizes

7.1. Each sweepstake draw will specify the method of entry, closing date, and prize details.
7.2. Entries received after the closing date will not be considered.
7.3. Winners will be selected at random and notified through the App or via the registered email address.
7.4. Prizes are non-transferable and may not be exchanged for cash unless otherwise stated.
7.5. The Operator reserves the right to substitute a prize of equivalent or greater value if a prize becomes unavailable.
7.6. Failure by a winner to respond within the stated timeframe may result in forfeiture of the prize.
7.7. Any taxes, customs duties, or fees arising from receipt of a prize are the sole responsibility of the winner.

8. Fees, Revenue, and Prize Funding

8.1. The App may display advertisements, sponsorships, or other monetisation features to fund prizes.
8.2. All prize funding originates from revenue generated by the Operator through legitimate business activities.
8.3. No User payments or purchases are required for entry into any sweepstake.
8.4. The Operator retains all revenue derived from app operations and may allocate a portion thereof for prize funding or promotional use.

9. Data Protection and Privacy

9.1. The Operator processes all Personal Data in compliance with the UK Data Protection Act 2018 and UK GDPR.
9.2. User data is collected solely for the purpose of operating the App, managing sweepstakes, and verifying eligibility.
9.3. User data will not be sold, shared, or transferred outside of SREGF Enterprises.
9.4. The Operator will not use Personal Data for marketing or unrelated purposes without the User’s explicit consent.
9.5. Users have the right to access, correct, or request deletion of their data by contacting sregfenterprises@gmail.com.
9.6. For full details on how we collect, store, and protect your information, please review our Privacy Policy below.

10. Intellectual Property Rights

10.1. All intellectual property in the App, including software, graphics, text, and design, is owned by or licensed to SREGF Enterprises.
10.2. Users are granted a limited, non-exclusive, revocable licence to use the App for personal, non-commercial purposes.
10.3. No part of the App may be copied, modified, distributed, or exploited without prior written consent from the Operator.

11. Prohibited Conduct

Users must not:

Attempt to manipulate or interfere with sweepstake outcomes;

Use automated systems, scripts, or bots to enter or participate;

Create multiple accounts to increase chances of winning;

Upload, post, or transmit unlawful, offensive, or misleading content; or

Use the App for any fraudulent, illegal, or unauthorised purpose.

12. Termination and Suspension of Accounts

12.1. The Operator may suspend or terminate any account that violates these Terms or engages in suspicious activity.
12.2. Users may close their accounts at any time by following in-app account deletion procedures.
12.3. Termination of an account will not affect any legal rights or obligations accrued prior to termination.

13. Limitation of Liability

13.1. The App and its content are provided “as is” without any warranties, express or implied.
13.2. The Operator does not guarantee uninterrupted or error-free App operation.
13.3. The Operator shall not be liable for any indirect, incidental, or consequential loss arising from use of the App or participation in any sweepstake.
13.4. Nothing in these Terms limits liability for death or personal injury caused by negligence, fraud, or any other liability that cannot be excluded by law.

14. Changes to Terms

14.1. The Operator reserves the right to modify these Terms at any time.
14.2. Updates will take effect immediately upon posting within the App or on the Operator’s website.
14.3. Continued use of the App after changes are posted constitutes acceptance of the revised Terms.

15. Governing Law and Jurisdiction

15.1. These Terms are governed by and construed in accordance with the laws of Scotland, United Kingdom.
15.2. Any disputes arising out of or in connection with these Terms shall be subject to the exclusive jurisdiction of the Scottish courts.

16. Contact Information

For any questions, concerns, or requests under these Terms, please contact:

SREGF Enterprises
Email: sregfenterprises@gmail.com

Registered Location: Scotland, United Kingdom

---

Privacy Policy

Effective Date: [Insert Date]
Operator: SREGF Enterprises (Sweepstake App)

1. Purpose

This Privacy Policy explains how SREGF Enterprises (“we,” “us,” or “our”) collects, uses, stores, and protects your Personal Data when you use the Sweepstake App (“the App”).

2. Data We Collect

We may collect and process the following data:

Account Information: Name, email address, and date of birth.

Technical Information: Device identifiers, IP address, operating system, and app version.

Usage Data: Interactions, sweepstake entries, and engagement activity.

Verification Data: Proof of identity or age (if requested).

3. How We Use Your Data

We use your Personal Data to:

Operate and administer sweepstakes and prize draws;

Verify eligibility and contact winners;

Ensure compliance with legal obligations;

Improve app performance and user experience; and

Communicate updates relating to your account or sweepstakes participation.

We do not sell or rent your data to third parties.

4. Legal Basis for Processing

We process your data under the following legal bases:

Performance of a contract: to provide you access to sweepstakes;

Legal obligation: to comply with data protection and contest laws;

Legitimate interests: to operate, secure, and improve our services.

5. Data Retention

We retain Personal Data only as long as necessary to fulfil the purposes outlined above, or as required by law.
Account data may be retained for up to 12 months following account closure for legal or audit purposes, after which it will be securely deleted.

6. Data Security

We implement technical and organisational safeguards to protect your data from unauthorised access, alteration, or disclosure. This includes encrypted data transmission and secure storage practices.

7. Your Rights

Under UK GDPR, you have the right to:

Access your Personal Data;

Request correction of inaccurate data;

Request deletion of your data;

Restrict or object to processing; and

Request a copy of your data (data portability).

To exercise these rights, contact us at sregfenterprises@gmail.com.

8. Data Transfers

Your data is processed and stored in the United Kingdom. If transferred internationally (e.g. for cloud hosting), we ensure appropriate safeguards in compliance with UK GDPR.

9. Cookies and Analytics

The App may use cookies or analytics tools to improve functionality and understand user behaviour. Users can disable cookies via device settings, though some features may not function correctly.

10. Updates to this Policy

We may update this Privacy Policy from time to time. Updates will take effect immediately upon posting within the App or on our website.

11. Contact Information

For questions or concerns regarding your privacy, please contact:
SREGF Enterprises
Email: sregfenterprises@gmail.com

Registered Location: Scotland, United Kingdom"""

// By splitting the text into paragraphs, the initial processing is much faster.
private val termsAsParagraphs = termsAndConditionsContent.split("\n\n")

@Composable
fun TermsAndConditionsScreen(modifier: Modifier = Modifier, onReturnClicked: () -> Unit) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Terms, Conditions, and Privacy Policy",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))

        // Use a LazyColumn for performance
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(termsAsParagraphs) { paragraph ->
                Text(paragraph)
                Spacer(Modifier.height(8.dp)) // Add space between paragraphs
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = onReturnClicked) {
            Text("Return to Welcome Screen")
        }
    }
}
