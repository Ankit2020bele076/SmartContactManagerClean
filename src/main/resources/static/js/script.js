let currentTheme = getTheme();



document.addEventListener('DOMContentLoaded',(event) => {changeTheme()})

function changeTheme(){
    document.querySelector("html").classList.add(currentTheme);
    const changeThemeButton = document.querySelector("#theme_change_button");
    if(currentTheme == "dark"){
        changeThemeButton.querySelector("span").textContent = "Light"
    }else{
        changeThemeButton.querySelector("span").textContent = "Dark"
    }
    
    changeThemeButton.addEventListener("click",(event) => {
        let oldtheme = currentTheme;
        if(currentTheme == "dark"){
            currentTheme = "light";
            changeThemeButton.querySelector("span").textContent = "Dark"
        }else{
            currentTheme = "dark";
            changeThemeButton.querySelector("span").textContent = "Light"
        }
        setTheme(currentTheme);
        document.querySelector("html").classList.remove(oldtheme);
        document.querySelector("html").classList.add(currentTheme);
    })
}

function setTheme(theme){
    localStorage.setItem("theme",theme)
}

function getTheme(){
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}