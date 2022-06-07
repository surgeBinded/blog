const printHtml = () => {
    const nav = document.createElement("nav");
    nav.setAttribute("class", "navbar")
    nav.innerHTML = `
        <img src="img/avataaars.png" width="60" height="60" class="logo" alt=""/>
        <ul class="link-container">
            <li class="link-item"><a href="/index.html" class="link">home</a></li>
            <li class="link-item"><a href="/editor.html" class="link">editor</a></li>
        </ul>
    `;
    document.querySelector("body").append(nav);
}
printHtml();