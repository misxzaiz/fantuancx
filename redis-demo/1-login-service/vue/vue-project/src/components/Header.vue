<script lang="ts">
    import axios from '../axios/index'
  
    declare var window: Window
    export default {
        data() {
            return {

            };
        },
        mounted() {

        },
        methods: {
            tran(str) {
                window.location.href = str
            },
            logout() {

                let token = localStorage.getItem('token')
                axios.get('logout/'+token)
                .then(res => {
                    console.log(res.data.message)
                })
                localStorage.removeItem('token')
            }
        },
        components: {

        }
    }
</script>

<template>
    <el-menu
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
    >
        <el-menu-item index="0" @click="tran('/')">饭团</el-menu-item>
        <div class="flex-grow" />
        <el-sub-menu index="1">
            <template #title>工作区</template>
            <el-menu-item index="1-1" @click="tran('login')">登录</el-menu-item>
            <el-menu-item index="1-2" @click="logout">注销</el-menu-item>
            <el-menu-item index="1-3" @click="tran('controller')">控制台</el-menu-item>
        </el-sub-menu>
    </el-menu>
</template>

<style scoped>
    .flex-grow {
        flex-grow: 1;
    }
</style>
