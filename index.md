---
---
<!-- Profile Pic Flip -->
<style>
.flip-card {
  background: transparent;
  width: 210px;
  height: 260px;
  perspective: 1000px;
  margin: -90px auto 0px;
  curson: pointer;
}
.flip-card-inner {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.7s;
  transform-style: preserve-3d;
}
.flip-card:hover .flip-card-inner {
  transform: rotateY(180deg);
}
.flip-card-front, .flip-card-back {
  position: absolute;
  width: 100%;
  height: 100%;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
  border: 4px solid #003366;
  border-radius: 8px;
}
.flip-card-front {
  background: #fff;
}
.flip-card-back {
  background: #003366;
  color: white;
  transform: rotateY(180deg);
  display: flex;
  align-items: flex-start;
  justify-content: center;
  padding: 15px;
  box-sizing: border-box;
  text-align: left;
  font-size: 13px;
  font-weight: bold;
  line-height: 1.5;
  font-family: "Times New Roman", Times, serif;
}
</style>

<div align="center" style="line-height:0;">
  <div class="flip-card" id="profileFlip" onclick="this.classList.toggle('flipped')">
    <div class="flip-card-inner">
      <div class="flip-card-front">
        <img src="Assets/moises.jpg" alt="Moises Sanchez" width="100%" style="display:block; height:100%; object-fit:cover; border-radius:4px;">
      </div>
 <div style="display:flex; align-items:center; gap:6px;">
    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#e3e3e3"><path d="M367-527q-47-47-47-113t47-113q47-47 113-47t113 47q47 47 47 113t-47 113q-47 47-113 47t-113-47ZM160-160v-112q0-34 17.5-62.5T224-378q62-31 126-46.5T480-440q66 0 130 15.5T736-378q29 15 46.5 43.5T800-272v112H160Z"/></svg>
    <span>Moises Sanchez</span>
        <div><b>Age:</b> 28 </div>
        <div><b>Role:</b> CS Student / Process Engineer </div>
      </div>
    </div>
  </div>
  <img src="Assets/snhulogo.png" alt="SNHU Logo" width="110" style="display:block; margin-top:10px;">
</div>
---
<div style="text-align: center; color: #808080;"><i>"Good code can be improved. Great code taught me how" <br> -Moises</i> </div>
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
    <img src="Assets/thumbnailYT.png" alt="Watch my initial code review" width="400" style="display:block; border: 4px solid #D32F2F; border-radius: 8px;">
  </a>
</p>

<div align="center" style="line-height:1.2;">
  <h4 style="margin:6px;">
    <a href="Assets/CodeReview_transcript.srt" download> ☞ Download the Video Transcript </a>
  </h4>
  <h4 style="margin:6px;">
    <a href="https://github.com/Moises2121/moises2121.github.io/tree/originalartifact/app/src/main/java/com/example/weighttracker" target="_blank"> ☞ See Original Code (Original version) </a>
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
## Software Design & Engineering
<div style="text-align: justify;">
The first version of my <b>Weight Tracking Application</b> contains severe security concerns that must be addressed prior to production release. While it was a great project for the Mobile Architecture course, it lacked multi-user functionality and fundamentals of a more complex data-layer. I did not have enough time to cover those vulnerabilites during that course, however, after continuing my student career at SNHU, I was able to identify and mitigate these issues.
</div>

<p align="left">
  <a href="./SoftwareEngineering.html" style="display:inline-block; padding:10px 20px; background-color:#238636; color:white; text-decoration:none; border-radius:6px; font-weight:bold;">Go to Enhancement One</a>
</p>
---
## Algorithms & Data Structures
<div style="text-align: justify;">
Initially, I developed the application using an arrayList that scans the entire weights database to collect the latest five entries. While it was good for an initial application, it's important to keep in mind the audience and collected data can grow over time. I apply techniques learned from CS300 to implement a binary search tree (BST) that supports faster lookups, improving peformance and allowing scalability.
</div>

<p align="left">
  <a href="./Algorithms.html" style="display:inline-block; padding:10px 20px; background-color:#238636; color:white; text-decoration:none; border-radius:6px; font-weight:bold;">Go to Enhancement Two</a>
</p>
---
## Databases
<div style="text-align: justify;">
<i>Under construction (Completion ETA 10.04.26)...</i>
</div>

<p align="left">
  <a href="./Databases.html" style="display:inline-block; padding:10px 20px; background-color:#238636; color:white; text-decoration:none; border-radius:6px; font-weight:bold;">Go to Enhancement Three</a>
</p>
---
## Contact Me
<b>Email:</b> moises.sanchez1@snhu.edu
  <div style="margin-bottom:10px;">
    <a href="https://github.com/moises2121" target="_blank" style="text-decoration:none; display:flex; align-items:center; font-weight:600; color:#0a66c2;">
      <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg" width="25" height="25" style="margin-right:8px;" />
      Follow me on GitHub
    </a>
  </div>
  <div>
    <a href="https://www.linkedin.com/in/moises-sanchez-9ab510361/" target="_blank" style="text-decoration:none; display:flex; align-items:center; font-weight:600; color:#0a66c2;">
      <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/linkedin/linkedin-plain.svg" width="25" height="25" style="margin-right:8px;" />
      Follow me on LinkedIn
    </a>
  </div>
  <div style="margin-top:10px;">
    <a href="https://www.youtube.com/@moisesgsg" target="_blank" style="text-decoration:none; display:flex; align-items:center; font-weight:600; color:#0a66c2;">
      <img src="https://upload.wikimedia.org/wikipedia/commons/0/09/YouTube_full-color_icon_%282017%29.svg" width="25" height="25" style="margin-right:8px;" />
      Follow me on YouTube
    </a>
  </div>

  <!-- Darkmode.js by Sandoche - https://github.com/sandoche/Darkmode.js - MIT License -->
  <script src="https://cdn.jsdelivr.net/npm/darkmode-js@1.5.7/lib/darkmode-js.min.js"></script>
  <script>
    function addDarkmodeWidget() {
      new Darkmode({
        bottom: '32px',
        right: '32px',
        time: '0.5s',
        mixColor: '#fff',
        backgroundColor: '#fff',
        buttonColorDark: '#100f2c',
        buttonColorLight: '#fff',
        saveInCookies: true,
        label: '🌓',
        autoMatchOsTheme: true
      }).showWidget();
    }
    window.addEventListener('load', addDarkmodeWidget);
  </script>
