<style>

</style>

<template>

  <Header></Header>

  <el-row :gutter="20">
    <el-col :span="4"></el-col>
    <el-col :span="16"><ArticleList></ArticleList></el-col>
    <el-col :span="4"></el-col>
  </el-row>

</template>


<script type="text/ecmascript-6">

  import Header from '../components/header/header.vue'
  import ArticleList from '../components/markdown/articleList.vue'

  export default {
    data() {
      return {

      };
    },
    components: {
      Header,
      ArticleList
    },
    methods: {
      router(uri) {
            this.$router.push(uri)
      },
    },
  };
</script>