<template>
    <div>
      <el-form :model="form" ref="form" :rules="rules" label-width="80px" class="login">
        <el-form-item label="账号" prop="username">
            <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="login">登录</el-button>
            <el-button type="primary" @click="register" disabled>注册</el-button>
        </el-form-item>
    </el-form>
    </div>
</template>
  
<script type="text/ecmascript-6">
  import axios from 'axios'

  export default {
    data() {
      return {
        form: {
          username: '',
          password: ''
        },
        rules: {
          username: [{required: true, message: '请输入账号！', trigger: 'blur'}],
          password: [{required: true, message: '请输入密码！', trigger: 'blur'}]
        }
      };
    },
    methods: {
      login() {  
        
        axios.post(window.$uriReq + '/login',this.form)
        .then(res => {
          if(res.data.code === 200){
            localStorage.setItem("token",res.data.data);
            this.$message({
              message: res.data.message,
              type: 'success'
            })
            this.$router.push('/')
          }else{
            this.$message({
              message: res.data.message,
              type: 'error'
            })
          }
        })
        .catch(error => {
          this.$message({
              message: error,
              type: 'error'
          })   
        })
      },
      register(){}
    },
  };
  </script>
  