import $ from 'jquery'

export default {
    namespace: true,
    state: {
        id: "",
        username: "",
        photo: "",
        token: "",
        is_login: false,
        pulling_info: true,  // 是否正在从云端拉取信息
    },
    getters: {
    },
    // 同步放mutations

    // 异步放actions
    actions: {
        login(context, data) {
            
            $.ajax({
                url: "http://127.0.0.1:1118/user/token/",
                type: "post",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    username: data.username,
                    password: data.password,
                }),
                success(resp) {
                    if (resp.message === "success") {
                        localStorage.setItem("jwt_token", resp.token);
                        // localStorage.setItem("username", resp.username);
                        // localStorage.setItem("role", resp.role);
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                        console.log(resp);
                    } else {
                        data.error(resp);
                    }

                },
                error(resp) {
                    data.error(resp);
                }
            });
        },
    },
    modules: {
    }
}