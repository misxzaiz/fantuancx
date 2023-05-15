<style>

</style>

<template>
  <Header></Header>
  <ArticleList></ArticleList>
  <Content :name="'2023-03-25-微信小程序实战.md'"></Content>
</template>


<script type="text/ecmascript-6">

  import Header from '../components/header/header.vue'
  import Content from '../components/markdown/content.vue'
  import ArticleList from '../components/markdown/articleList.vue'

  export default {
    data() {
      return {

      };
    },
    components: {
      Header,
      Content,
      ArticleList
    },
    methods: {
      router(uri) {
            this.$router.push(uri)
      },
    },
  };
</script>