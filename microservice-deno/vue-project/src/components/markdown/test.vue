<style>

</style>

<template>
    <div v-html="htmlText"></div>
</template>


<script type="text/ecmascript-6">

  import { marked } from 'marked';
  import { mangle } from 'marked-mangle';
  import { gfmHeadingId } from 'marked-gfm-heading-id';
  import axios from "axios";

  export default {
    name: 'Content',
    props: {
        id: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            markdownText: '# Hello, world!',
            htmlText: "",
        };
    },
    mounted() {
        let id = this.id
        // 初始化 marked 插件
        marked.use(mangle({mangle: false}));
        marked.use(gfmHeadingId({headerIds: false}));
        const token = localStorage.getItem('token')
          axios.get(window.$uriReq+'/markdownservice/content/'+id, {
            headers: {
              'Authorization': `${token}`
            }
          })
          .then(res => {        
            this.markdownText = res.data;
        this.htmlText = marked(this.markdownText);
            this.$message({
              message: 'success',
              type: 'success'
            })
          })
          .catch(error => {
            this.$message({
              message: error,
              type: 'error'
            })
          })
    } 
  };

</script>