<template>
    <ContentField v-if="!$store.state.user.pulling_info">
        <div class="row justify-content-md-center">
            <div class="col-3">
                <!-- @submit.prevent 点击提交按钮时，表单并不会提交，而是会触发绑定的函数，就像下边的onSubmith函数一样，
                 我们可以在函数里做想做的事情，比如参数完整性验证，ajax提交，对后台数据的处理等 -->
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <!-- v-model指令可以在表单 input、textarea以及select元素上创建双向数据绑定
                         它会根据控件类型自动选取正确的方法来更新元素。
                         注意：
                         1 在插值表达式中，只能设置简单的javascript表达式，不能设置复杂的表达式(例如for循环)。
                         2 在data值大小不改变的前提下，可以进行一般的 算术运算、比较运算、逻辑运算、三元操作符等运算使用，也可以通过常量进行数据体现。
                         3 插值表达式只能用在html标签的内容区域，不能用在其他地方。
                         4 {{}}花括号与变量之间为了美观，通常表达式左右各一个空格。 -->
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password"
                            placeholder="请输入密码">
                    </div>
                    <!-- vue中，使用{{}}双花括号，在html标签的“内容区域”中表现数据，这个技术称为插值表达式。 -->
                    <div class="message">{{ message }}</div>
                    <button type="submit" class="btn btn-primary">提交</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
/** https://blog.csdn.net/EchoLiner/article/details/130445600
 *   ref( ) 接受一个内部值，返回一个ref 对象，这个对象是响应式的、可更改的，且只有一个指向其内部值的属性 .value。
     ref() 将传入参数的值包装为一个带 .value 属性的 ref 对象。
     1、ref 对象是可更改的，即可以为 .value 赋予新的值
     2、ref 对象是响应式的，即所有对 .value 的操作都将被追踪，并且写操作会触发与之相关的副作用。(当ref的值为对象类型时，会用 reactive() 自动转换它的 .value。) 
     3、ref 在模板中的解包
     当 ref 在模板中作为顶层属性被访问时，它们会被自动“解包”，所以不需要使用 .value。
     4、ref 在响应式对象中的解包
     当一个 ref 被嵌套在一个响应式对象中，作为属性被访问或更改时，它会自动解包，因此会表现得和一般的属性一样：
     5、ref在数组和集合类型的解包
     跟响应式对象不同，当 ref 作为响应式数组或像 Map 这种原生集合类型的元素被访问时，不会进行解包。
     6、ts为 ref() 标注类型
     ref 会根据初始化时的值推导其类型：
*/

/** 
 * 传统的页面应用，是用一些超链接来实现页面切换和跳转的。
 * 在vue-router单页面应用中，则是路径之间的切换，实际上就是组件的切换。
 * 路由就是SPA（单页应用）的路径管理器。
 * 再通俗的说，vue-router就是我们WebApp的链接路径管理系统。
 */
import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '@/router';
import ContentField from '@/components/ContentField.vue';

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let message = ref('');
        let show_content = ref(false);

        // jwt_token未过期 保持登录状态
        const jwt_token = localStorage.getItem("jwt_token");
        const roleid = localStorage.getItem("roleid");
        if (jwt_token) {
            store.commit("updateToken", jwt_token);
            store.dispatch("getinfo", {
                success() {
                    if(roleid === "1"){
                        router.push({ name: "admin_home" });
                    }
                    else{
                        router.push({ name: "home" });
                    }
                    
                    store.commit("updatePullingInfo", false);
                },
                error() {
                    store.commit("updatePullingInfo", false);
                }
            })
        } else {
            store.commit("updatePullingInfo", false);
        }

        const login = () => {
            //每次提交清空message
            message.value = "";
            /**this.$store.dispatch是用于触发action的方法。Actions类似于mutations，不同之处在于：
                Actions提交的是mutation，而不是直接变更状态。
                Actions可以包含任意异步操作。 */
            store.dispatch("login", {
                //ref取值
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success(Response) {
                            if (Response.roleid == 1) {
                                router.push({ name: 'admin_home' });
                            } else {
                                router.push({ name: 'home' });
                            }
                        }
                    })
                },
                error() {
                    message.value = "用户名或密码错误";
                }
            })
        }

        return {
            username,
            password,
            message,
            show_content,
            login,
        }
    }
}
</script>

<style scoped>
button {
    width: 100%;
}

div.message {
    color: red;
}
</style>