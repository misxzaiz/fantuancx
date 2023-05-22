<style>
  .flex-grow {
    flex-grow: 1;
  }
  .el-menu {
    font-weight: bolder;
    margin: 0;
    padding: 0;
  }
  .el-menu-logo {
    font-size: x-large;
  }
</style>

<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo el-menu" mode="horizontal" :ellipsis="false" @select="handleSelect" >
    <el-menu-item index="0" @click="router('/')" class="el-menu-logo">Fantuan</el-menu-item>
    <div class="flex-grow"/>
    <el-sub-menu index="1">
        <template #title>工作台</template>
        <el-menu-item index="1-1" @click="router('/login')">登录</el-menu-item>
        <el-menu-item index="1-2" @click="logout">注销</el-menu-item>
        <el-menu-item index="1-3" @click="demo">Demo</el-menu-item>
        <el-menu-item index="1-4" @click="router('/controller')">控制台</el-menu-item>
        <el-menu-item index="1-5" @click="trantoURL('/swagger-ui.html')">API</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>


<script type="text/ecmascript-6">
  import axios from 'axios'


  export default {
    data() {
      return {

      };
    },
    methods: {
        router(uri) {
            this.$router.push(uri)
        },
        trantoURL(url){
          window.location.href=window.$uriReq+url
        },
        demo(){
          const token = localStorage.getItem('token')
          axios.get(window.$uriReq+'/demoservice/demo', {
            headers: {
              'Authorization': `${token}`
            }
          })
          .then(res => {
            this.$message({
              message: res.data,
              type: 'success'
            })
          })
          .catch(error => {
            this.$message({
              message: error,
              type: 'error'
            })
          })
        },
        logout() {
          localStorage.removeItem('token');
          this.$message({
            message: '注销成功！',
            type: 'success'
          })
        }
    },
  };
</script>