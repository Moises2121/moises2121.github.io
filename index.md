---
---
<div align="center" style="line-height: 0;">
  <img src="Assets/moises.jpg" alt="Moises Sanchez" width="210" style="display:block; margin-top: -90px; margin-bottom: 0px;">
  <img src="Assets/snhulogo.png" alt="SNHU Logo" width="110" style="display:block;">
</div>
---
<div id="typing-wrapper"><span id="typing"></span><span id="cursor">|</span></div>
---
## Introduction
<div style="text-align: justify;">
  <p>
Welcome to my CS499 ePortfolio. Here, I showcase my skills obtained throught the BS in CS degree in a professional and descriptive manner, along with mitigation steps for the challenges encountered. I will discuss and review improved elements for the enhancement of my <b>Weight Tracking Application</b>.
I’m aiming to develop a fully functional application that follows best practices and enhances user experience, as well as ensuring database is properly handled. More specifically, ensuring the code is readable and sustainable, the UI and UX elements are adequate for the desired audience and using Room library for Android to handle database components. This page will include a self assesment, video code review, and the before and after artifact for each enhancement- each containing a descriptive narrative.
  </p>
</div>
---
<div style="background:#f1f8e9; border:1px solid #dcedc8; border-radius:10px; padding:10px 20px;" markdown="1">

## Table of Contents
* TOC
* 
{:toc}

</div>
---
## Self Assesment
<div style="text-align: justify;">
  <p>
<i>Under construction (Completion ETA 10.16.26)...</i>
  </p>
</div>
---
## Code Review
<div style="text-align: justify;">
  <p>
First, my developer's code review. This video outlines the current structure of the application and its services, then it shows the functionality of each module. In this code review, the areas of weaknesses are pointed out, using a developer's <a href="Assets/Developer_Checklist.pdf" target="_blank">checklist</a> for best practices. Then, the three enhancements will be discussed along with the steps that I must take in order to fulfill the enhanced version. At the end, this strategy gives us (developers) an opportunity to find vulnerabilites before launching the final product.
</p>
</div>

<p align="center">
  <a href="https://youtu.be/efCo_He-T5c" target="_blank">
    <img src="Assets/thumbnailYT.png" alt="Watch my initial code review" class="ytimage-popup">
  </a>
</p>

<div align="center" style="line-height:1.2;">
  <h4 style="margin:6px;">
    <a href="Assets/CodeReview_transcript.srt" download id="downloadBtn" style="display:inline-flex; align-items:center; gap:6px; justify-content:center; text-decoration:none;">
      <span class="material-symbols-outlined" style="font-size:20px;">link</span>
      Download the Video Transcript
    </a>
    <script>
  document.getElementById('downloadBtn').addEventListener('click', function(e){
    e.preventDefault();
    const url = this.href;
    const ok = confirm("Are you sure you want to download?");
    if(ok){
      window.location.href = url;
      }
    });
  </script>
    
  </h4>
  <h4 style="margin:6px;">
   <a href="https://github.com/Moises2121/moises2121.github.io/tree/originalartifact/app/src/main/java/com/example/weighttracker" target="_blank" style="display:inline-flex; align-items:center; gap:6px; justify-content:center; text-decoration:none;">
      <span class="material-symbols-outlined" style="font-size:20px;">link</span>
      See Original Code (First version)
    </a>
  </h4>
</div>
      
<br>

<div style="text-align: justify;">
  <p>
As seen in the code review, the main areas of focus are the structural architecture of the application, adding repositories for weight and users, using a binary search tree (BST) for <i>O log n</i> lookups, refactoring via <code class="language-plaintext highlighter-rouge">Room</code>, using foreign keys to handle databases, and adding professional commenting styles that allow reusability and readability practices.
<br>
I will use my skills in Software Design & Engineering, Algorithms & Data Structures and Databases to articulate best practices and deliver a professional refactored artifact. Feel free to read through each category's narratives, challenges as well as the artifact's before and after status.
</p>
</div>
---
## <span class="material-symbols-outlined" style="vertical-align:middle; color:#159957; font-size:28px;">engineering</span> Software Design & Engineering

<div style="text-align: justify;">
The first version of my <b>Weight Tracking Application</b> contains severe security concerns that must be addressed prior to production release. While it was a great project for the Mobile Architecture course, it lacked multi-user functionality and fundamentals of a more complex data-layer. I did not have enough time to cover those vulnerabilites during that course, however, after continuing my student career at SNHU, I was able to identify and mitigate these issues.
</div>

<p align="left">
  <a href="./SoftwareEngineering.html" style="display:inline-block; padding:10px 20px; background-color:#238636; color:white; text-decoration:none; border-radius:6px; font-weight:bold;">Go to Enhancement One</a>
</p>
---
## <span class="material-symbols-outlined" style="vertical-align:middle; color:#159957; font-size:28px;">account_tree</span> Algorithms & Data Structures

<div style="text-align: justify;">
Initially, I developed the application using an arrayList that scans the entire weights database to collect the latest five entries. While it was good for an initial application, it's important to keep in mind the audience and collected data can grow over time. I apply techniques learned from CS300 to implement a binary search tree (BST) that supports faster lookups, improving peformance and allowing scalability.
</div>

<p align="left">
  <a href="./Algorithms.html" style="display:inline-block; padding:10px 20px; background-color:#238636; color:white; text-decoration:none; border-radius:6px; font-weight:bold;">Go to Enhancement Two</a>
</p>
---
## <span class="material-symbols-outlined" style="vertical-align:middle; color:#159957; font-size:28px;">database</span> Databases

<div style="text-align: justify;">
The first release was functional but stored plain text passwords within the database, risking user privacy. For this Databases enhancement, the database layer is strenghtened by storing user passwords as BCrypt hashes that help prevent credential leaks, and an additional reinforcement is using ForeignKey with onDelete CASCADE so that if a user is deleted, all their personal data including weight entries are removed from the records, thus preventing dangling data.
</div>

<p align="left">
  <a href="./Databases.html" style="display:inline-block; padding:10px 20px; background-color:#238636; color:white; text-decoration:none; border-radius:6px; font-weight:bold;">Go to Enhancement Three</a>
</p>
---
  {% include contact.html %}
  
  {% include floating-menu.html %}
  
  {% include typing.html %}
