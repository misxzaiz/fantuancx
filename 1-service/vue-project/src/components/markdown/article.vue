<style>

</style>

<template>
    <Header></Header>
    <el-row :gutter="20">
        <el-col :span="4"></el-col>
        <el-col :span="16"><Content :name="name"></Content></el-col>
        <el-col :span="4"></el-col>
    </el-row>
</template>


<script type="text/ecmascript-6">

    import Header from '../header/header.vue'
    import Content from './content.vue'
    


    export default {

        data() {
            return {
                name: this.$route.query.name
            };
        },
        components: {
            Header,
            Content
        }
    };
</script>