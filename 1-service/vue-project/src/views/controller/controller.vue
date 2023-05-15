<template>
    <Header/>
    <el-tabs :tab-position="'left'" class="demo-tabs">
        <el-tab-pane label="用户管理">
            <User></User>
        </el-tab-pane>
        <el-tab-pane label="Config">Config</el-tab-pane>
        <el-tab-pane label="Role">Role</el-tab-pane>
        <el-tab-pane label="Task">Task</el-tab-pane>
    </el-tabs>
</template>
  
<script type="text/ecmascript-6">

    import Header from '../../components/header/header.vue'
    import User from './user/user.vue'

    export default {
        data() {
            return {

            };
        },
        methods: {
            router(uri) {
                this.$router.push(uri)
            }
        },
        components: {
            Header,
            User
        }
    };
</script>
  