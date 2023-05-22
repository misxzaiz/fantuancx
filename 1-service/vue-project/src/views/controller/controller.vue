<style scoped>

</style>

<template>
    <Header/>
    <el-tabs :tab-position="'left'" class="demo-tabs">
        <el-tab-pane label="用户管理"><User/></el-tab-pane>
        <el-tab-pane label="文章管理"><Article/></el-tab-pane>
        <el-tab-pane label="Role">Role</el-tab-pane>
        <el-tab-pane label="Task">Task</el-tab-pane>
    </el-tabs>
</template>
  
<script type="text/ecmascript-6">

    import Header from '../../components/header/header.vue'
    import User from './user/user.vue'
    import Article from './article/article.vue'

    export default {
        data() {
            return {

            };
        },
        methods: {

        },
        components: {
            Header,
            User,
            Article
        }
    };
</script>
  