---
layout: default
title: Software Design & Engineering
---
# Artifact's Origin

<p> Artifact : CS360 final project “Weight Tracking App” (Kotlin, XML, SQLite)
Developed on August 2026 for Mobile Architecture and Programming at SNHU
</p>
<div style="text-align: justify;">
<p>
The application was developed as part of a project that required Android Studio environment. It’s original goal was to design a friendly user interface that follows Android’s design foundations and best practices. The application’s requirements were to provide CRUD operations and a trusted database, either local or online, where user’s data such as weight, username and passwords can be stored.
</p>
</div>
---
# Justification
<div style="text-align: justify;">
<p>The original version of this submission implemented an unstructured SQLite helper that verifies the user’s authentications but violates single responsibility principle. The DashboardActivity module handles most of the operations for the application’s functionalities but hardcoded “test” username breaks the multi-user support, which does not follow best practices.
In this artifact, the CRUD operations must work properly without causing memory leaks, or accidentally exposing another user’s personal data. The modules currently don’t follow a structured architecture that can be used for improvements, so the enhancement includes refactoring the tree’s architecture in a professional manner, as well as adding accurate documentation with in-line comments to improve the application’s sustainability and readability. 
</p>
</div>
---
# Enhancement Steps
## Step One
<p>Do...</p>
## Step Two
<p>Do...</p>
## Step Three
<p>Do...</p>
## Step Four
<p>Do...</p>
---
# Challenges
<p>I encountered...</p>
---
# Outcomes
<p>This artifact...</p>

<br>

<hr>
<p align="left">
  <a href="./index.html" style="display:inline-block; padding:8px 16px; background-color:#f6f8fa; color:#24292f; text-decoration:none; border-radius:6px; border:1px solid #d0d7de; font-weight:bold;">← Home</a>
</p>
