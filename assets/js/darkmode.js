// Darkmode.js by Sandoche - https://github.com/sandoche/Darkmode.js - MIT License 
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
    label: '💡',
    autoMatchOsTheme: true
  }).showWidget();
}
window.addEventListener('load', addDarkmodeWidget);
