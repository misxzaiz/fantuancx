<script lang="ts">

import axios from '../../axios/index';
import Header from '../../components/Header.vue'

    export default {
        data() {
            return {
                form: {
                    phone: '',
                    password: '',
                }
            };
        },
        mounted() {

        },
        methods: {
            onSubmit() {
                console.log(this.form)
                axios.post('login',this.form)
                    .then(res => {
                        localStorage.setItem('token','token')
                        console.log(res)
                    })
            }
        },
        components: {
            Header
        }
    }
</script>

<template>
    <Header />
    <div class="login">
        <el-form :model="form">
            <el-form-item label="手机">
                <el-input v-model="form.phone" />
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="form.password" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">登录</el-button>
                <el-button type="primary" disabled >注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style scoped>
    .login {
        padding: 10%;
    }
</style>
