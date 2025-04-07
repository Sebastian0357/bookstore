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
    mutations: {
        updateUser(state, user) {
            console.log('updateUser mutation called:', user);  // Log updateUser data
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.is_login = user.is_login;
            state.roleId = user.roleId;
        },
        updateToken(state, token) {
            console.log('updateToken mutation called:', token);  // Log token data
            state.token = token;
        },
        logout(state) {
            console.log('logout mutation called');  // Log logout event
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.is_login = false;
            state.roleId = "";
        },
        updatePullingInfo(state, pulling_info) {
            console.log('updatePullingInfo mutation called:', pulling_info);  // Log pulling_info state
            state.pulling_info = pulling_info;
        },
    },
    // 异步放actions
    actions: {
        login(context, data) {
            console.log('login action started with data:', data);  // Log input data
            $.ajax({
                url: "http://127.0.0.1:1118/user/token/",
                type: "post",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    username: data.username,
                    password: data.password,
                }),
                success(resp) {
                    console.log('login response received:', resp);  // Log response from login
                    if (resp.message === "success") {
                        localStorage.setItem("jwt_token", resp.token);
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    console.error('login request failed:', resp);  // Log error response
                    data.error(resp);
                }
            });
        },
        getinfo(context, data) {
            console.log('getinfo action started');  // Log getinfo action start
            $.ajax({
                url: "http://127.0.0.1:1118/user/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success(resp) {
                    console.log('getinfo response received:', resp);  // Log response from getinfo
                    if (resp.message === "success") {
                        context.commit("updateUser", {
                            //解构resp
                            ...resp,
                            is_login: true,
                        });
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    console.error('getinfo request failed:', resp);  // Log error response
                    data.error(resp);
                }
            });
        },
        
        logout(context) {
            console.log('logout action started');  // Log logout action start
            localStorage.removeItem("jwt_token");
            context.commit("logout");
        }
    },
    modules: {
    }
}
