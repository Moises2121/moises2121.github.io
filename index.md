---
---
<div align="center" style="line-height: 0;">
  <img src="Assets/moises.jpg" alt="Moises Sanchez" width="210" style="display:block; margin-top: -90px; margin-bottom: 0px;   border: 4px solid rgb(248, 250, 252);
  border-radius: 15px;">
  <img src="Assets/snhulogo.png" alt="SNHU Logo" width="115" style="display:block;">
</div>
---
<div id="typing-wrapper"><span id="typing"></span><span id="cursor">|</span></div>
---
<div style="background:#f1f8e9; border:1px solid #dcedc8; border-radius:10px; padding:10px 20px;" markdown="1">

## Table of Contents
{:.no_toc}

* TOC
* 
{:toc}

</div>
---
## <span class="material-symbols-outlined" style="vertical-align:middle; color:#159957; font-size:28px;">app_registration</span> Introduction

<div style="text-align: justify;">
  <p>
Welcome to my CS499 ePortfolio. Here, I showcase my skills obtained throught the BS in CS degree in a professional and descriptive manner, along with mitigation steps for the challenges encountered. I discuss and review improved elements for the enhancement of my <b>Weight Tracking Application</b>.
I’m aiming to develop a fully functional application that follows best practices and enhances user experience, as well as ensuring database is properly handled. More specifically, ensuring the code is readable and sustainable, the UI and UX elements are adequate for the desired audience and using Room library for Android to handle database components. This page will include a self assesment, video code review, and the before and after artifact for each enhancement - each containing a descriptive narrative.
  </p>
</div>
---
## <span class="material-symbols-outlined" style="vertical-align:middle; color:#159957; font-size:28px;">person</span> Self Assessment

<details class="details-dropdown">
<summary>Expand to Read <span style="font-weight:400; opacity:0.5; margin-left:auto;">(6 min read)</span></summary>
<div class="details-content" markdown="1">

Completing the coursework in `CS499` helped me strengthen and highlight my technical growth and redefine my personal goals. Through iterative projects, code reviews, full-stack development techniques and project proposals, I was able to comfortably produce maintainable and secure software. This ePortfolio showcases these skills by presenting not only the final artifact, but the reasoning behind each decision — which helped me focus on becoming a responsible developer using secure and user-centered design. Aside from my ePortfolio, courses such as CS305 (Software Security), CS340 (Client Server Development), CS360 (Mobile Architecture & Programming) helped me develop Agile methodologies to accomplish industry best practices, which overall helped strengthen my ability to work in a production-like environment that makes me a good candidate by aligning my skills to emerging trends.

Outside of this ePortfolio, the **Grazioso Salvare** project in CS340 demonstrated my ability to implement a normalized `MongoDB` solution with CRUD operations that indexed queries for a dog shelter organization, while maintaining stakeholder requirements on a functional dashboard. Additionally, in CS305 I learned to identify vulnerabilities, enforcing secure coding standards and using static analysis tools. By applying these principles, I was able to enhance this artifact to implement security hashing and providing a clean user interface.

<a href="https://github.com/Moises2121" target="_blank">Explore my artifacts <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg" width="25" height="25" style="margin-right:8px; background:white; border-radius:50%; padding:2px; vertical-align:middle; transform: translateY(-1px); margin-left:8px;"/></a>

The Computer Science program at SNHU reinforced the importance of collaborating through peer reviews, discussion boards and sharing repositories. By receiving feedback from my professor and reading the discussions from classmates with similar roadblocks, I was able to identify the weaknesses that were later mitigated in this artifact. By providing feedback, I was able to collaborate with my classmates and reflect on their challenges, plus my current experience in an engineering role helped me leverage a team-based foundation.

In Data Structures & Algorithms, the program guided me to evaluate computing solutions. I learned to analyze the complexity and select the adequate structures based on access patterns and data growth — ensuring solutions remain performant as requirements evolve. In Software Engineering, I developed an understanding of building maintainable architecture using industry patterns, version control and database design. In databases, I learned to evaluate database solutions that ensure data integrity, prevent duplication and improve query performance through key structures and indexing. These practices allow long term adaptability while protecting user's data.

Throughout the program, I quickly learned to adapt my communication skills to both technical and non-technical audiences. Through each course, I documented the challenges and compilation errors encountered, including type mismatches and unresolved references. For non-technical stakeholders, the notification system using `getLastNDays()` for instance, was designed to provide visual feedback that helps support decision-making without requiring technical background, thus adapting to the audience's needs.

The original code relied on full O(n) scans of the user's weight history. The enhanced version replaces it with `BST` using `TreeMap`, reducing search operations. Implementing `getHistoryAsBST` enforces encapsulation and protects data integrity. Altogether showing my ability to design and evaluate computing solutions while managing trade-offs between memory overhead and access time to ensure scalability.

Enhancement one and three showcase my software engineering and databases principles. In the first enhancement, I implemented industry tools such as `Room`, `LiveData`, and the repository pattern that build a maintainable architecture and deliver a professional and coherent application that does not break the original requirements. In enhancement three, I implemented a normalized database by using `Room` with primary key (id), foreign key (username referencing user table), and indexed queries to prevent full scans and duplicate rows. Evaluating these solutions using `DAO` and `SQLite` demonstrates my overall ability to build solutions that accomplish industry goals and maintain architectural integrity.

Security was one of my major focuses through all three enhancements. Enhancement one mitigated a privacy flaw where a hardcoded `test` user exposed personal data. Enhancement three implements security best practices by hashing the user's passwords via `BCrypt` before storage, enforcing foreign key constraints to prevent unauthorized access and ensuring each record is associated with a user. I kept all methods private and anticipated empty data exploits to further protect against data extraction and other integrity attacks, therefore ensuring information is protected prior to deployment.

My three enhancements demonstrate a full-stack and secure software lifecycle. Enhancement one focusing on software engineering and fixing collaborative failures, enhancement two focusing on algorithmic efficiency and enhancement three prioritizing database normalization and security. Together, this updated **Weight Tracking Application** delivers a functional, professional and secure product that is ready for real users. This range demonstrates my strengths in problem solving, clean and organized architecture practices and security-first mentality — preparing me to enter the computer science field as a reliable contributor.

</div>
</details>
---
## <span class="material-symbols-outlined" style="vertical-align:middle; color:#159957; font-size:28px;">find_in_page</span> Code Review
<div style="text-align: justify;">
  <p>
This video outlines the current structure of the application and its services, then it shows the functionality of each module. In this code review, the areas of weaknesses are pointed out, using a developer's <a href="Assets/Developer_Checklist.pdf" target="_blank">checklist</a> for best practices. Then, the three enhancements will be discussed along with the steps that I must take in order to fulfill the enhanced version. At the end, this strategy gives us (developers) an opportunity to find vulnerabilites before launching the final product.
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
As seen in the code review, the main areas of focus are the structural architecture of the application, adding repositories for weight and users, using a binary search tree (BST) for <i>O (log n + k)</i> lookups, refactoring via <code>Room</code>, using foreign keys to handle databases, and adding professional commenting styles that allow reusability and readability practices.
<br>
I will use my skills in Software Design & Engineering, Algorithms & Data Structures and Databases to articulate best practices and deliver a professional refactored artifact. Feel free to read through each category's narratives, challenges as well as the artifact's before and after statuses.
</p>
</div>
---
## <span class="material-symbols-outlined" style="vertical-align:middle; color:#159957; font-size:28px;">engineering</span> Software Design & Engineering

<div style="text-align: justify;">
The first version of my <b>Weight Tracking Application</b> contains severe security concerns that must be addressed prior to production release. While it was a great project for the Mobile Architecture course, it lacked multi-user functionality and fundamentals of a more complex data-layer. I did not have enough time to cover those vulnerabilites during that course, however, after continuing my student career at SNHU, I was able to identify and mitigate these issues.
</div>

<p align="left">
  <a href="./SoftwareEngineering.html" style="display:inline-block; padding:10px 20px; background-color:#238636; color:white; text-decoration:none; border-radius:6px; font-weight:bold;"
  class="enhancement-link">Go to Enhancement One <span class="material-symbols-outlined">
arrow_circle_right
</span></a>
</p>
---
## <span class="material-symbols-outlined" style="vertical-align:middle; color:#159957; font-size:28px;">account_tree</span> Algorithms & Data Structures

<div style="text-align: justify;">
Initially, I developed the application using an arrayList that scans the entire weights database to collect the latest five entries. While it was good for an initial application, it's important to keep in mind the audience and collected data can grow over time. I apply techniques learned from CS300 to implement a binary search tree (BST) that supports faster lookups, improving peformance and allowing scalability.
</div>

<p align="left">
  <a href="./Algorithms.html" style="display:inline-block; padding:10px 20px; background-color:#238636; color:white; text-decoration:none; border-radius:6px; font-weight:bold;" class="enhancement-link">Go to Enhancement Two <span class="material-symbols-outlined">
arrow_circle_right
</span></a>
</p>
---
## <span class="material-symbols-outlined" style="vertical-align:middle; color:#159957; font-size:28px;">database</span> Databases

<div style="text-align: justify;">
The first release was functional but stored plain text passwords within the database, risking user privacy. For the Databases enhancement, the database layer is strenghtened by storing user passwords as <code>BCrypt</code> hashes that help prevent credential leaks, and an additional reinforcement is using ForeignKey with onDelete CASCADE so that if a user is deleted, all their personal data including weight entries are removed from the records, thus preventing dangling data.
</div>

<p align="left">
  <a href="./Databases.html" style="display:inline-block; padding:10px 20px; background-color:#238636; color:white; text-decoration:none; border-radius:6px; font-weight:bold;" class="enhancement-link">Go to Enhancement Three <span class="material-symbols-outlined">
arrow_circle_right
</span></a>
</p>
---
---
  {% include contact.html %}
  
  {% include floating-menu.html %}
  
  {% include typing.html %}
