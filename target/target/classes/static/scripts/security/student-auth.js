if (sessionStorage.getItem(SESSION_STORAGE_LOGIN_TOKEN_NAME)) {
    let json = JSON.parse(sessionStorage.getItem(SESSION_STORAGE_LOGIN_TOKEN_NAME));
    if (json.role == "faculty") {
        window.location.replace(ROOT_PATH + "/pages/faculty/index.html");
    }
}
