<!--头部组件-->
<template>
    <div style="display: flex;line-height: 60px;">
        侧边栏
    </div>
</template>

<script>
    export default {
        name: "AdminHeader",
        data(){
            return {
                user : JSON.parse(sessionStorage.getItem('CurUser'))
            }
        },
        props:{
            icon:String
        },
        methods:{
            toUser(){
                console.log('to_user')

                this.$router.push("/Home")
            },
            logout(){
                console.log('logout')

                this.$confirm('您确定要退出登录吗？', '提示', {
                    confirmButtonText: '确定',  //确认按钮的文字显示
                    type: 'warning',
                    center: true, //文字居中显示

                })
                    .then(() => {
                        this.$message({
                            type:'success',
                            message:'退出登录成功！'
                        })

                        this.$router.push("/")
                        sessionStorage.clear()
                    })
                    .catch(() => {
                        this.$message({
                            type:'info',
                            message:'已取消退出登录！'
                        })
                    })

            },
            collapse(){
                this.$emit('doCollapse')
            }

        },
        //vue生命周期
//         beforecreate : 可以在这加个loading事件
//         created ：在这结束loading，还做一些初始数据的获取，实现函数自执行
//     mounted ： 在这发起后端请求，拿回数据，配合路由钩子做一些事情
//     beforeDestroy： 你确认删除XX吗？
//         destroyed ：当前组件已被删除，清空相关内容
        created(){
            this.$router.push("/Home")
        }

    }
</script>

<style scoped>

</style>