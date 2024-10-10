import $ from 'jquery'

export default {
    state: {
        id: "",
        username: "",
        photo: "",
        token: "",
        is_login: false,
    },
    getters: {
    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.is_login = user.is_login;
        },
        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.is_login = false;
        }
    },
    
    actions: {
        /**Ajax请求通过XMLHttpRequest对象发送请求，
            该对象有五个状态（readyState）：
            0-未初始化、
            1-正在初始化、
            2-发送数据、
            3-正在发送数据、
            4-完成 

            ajax的success和error方法根据响应状态码来触发。
            当XMLHttpRequest.status为200的时候，表示响应成功，此时触发success().
            其他状态码则触发error()。
            除了根据响应状态码外，ajax还会在下列情况下走error方法：
            返回数据类型不是JSON
            网络中断
            后台响应中断
        */
       /**
            在 Vuex 的 actions 中，context 是一个包含了与 Vuex 相关的属性和方法的对象。
            context 提供了一些方法和属性，用于在 actions 中与 state、mutations 和其他 actions 进行交互。 
        */
        login(context, data) {
            
            $.ajax({
                url: "http://127.0.0.1:3000/user/account/token/",
                type: "post",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    username: data.username,
                    password: data.password,
                }),
                success(resp) {
                    if (resp.message === "success") {
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            });
        },
        getinfo(context, data) {
            $.ajax({
                url: "http://127.0.0.1:3000/user/account/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    if (resp.message === "success") {
                        context.commit("updateUser", {
                            //解构resp
                            ...resp,
                            is_login: true,
                        });
                        data.success(resp)
                    } else {
                        data.error(resp)
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            });
        },
        
        logout(context) {
            context.commit("logout");
        }
    },
    modules: {
    }
}