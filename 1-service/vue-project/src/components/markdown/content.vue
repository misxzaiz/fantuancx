<style>
  h1 {
    margin: 0;
  }
  .htmlText {
    background: #fff;
    padding: 2%;
  }
  pre {
    border: black solid 0.5px;
    color: #f5f5f5;
    background-color: #0c0c0c;
    font-family: Consolas, Menlo, Courier, monospace;
    font-size: 1rem;
    line-height: 1.5;
    overflow: auto;
    padding: 0.5rem;
  }
  
  code {
    font-family: Consolas, Menlo, Courier, monospace;
    font-size: 1rem;
    line-height: 1.5;
  }
</style>

<template>
  <div v-html="htmlText" class="htmlText" ></div>
</template>

<script>
  import { marked } from 'marked';
  import { mangle } from 'marked-mangle';
  import { gfmHeadingId } from 'marked-gfm-heading-id';
  import hljs from "highlight.js"
  import "highlight.js/styles/monokai-sublime.css"
  import axios from "axios";

  export default {
    name: 'Content',
    props: {
      name: {
        type: String,
        required: true
      }
    },
    data() {
      return {
        htmlText: "",
        contentNav: [],
        headers: {
          'headers': {
            'Authorization': 'headers.Authorization.token'
          }
        },
      };
    },
    mounted() {
      
      this.getContent()
    },
    methods: {
      getContent() {
        let name = this.name
        // 初始化 marked 插件
        marked.use(mangle({mangle: false}));
        marked.use(gfmHeadingId({headerIds: false}));

        axios.post(window.$uriReq+'/markdownservice/content', {
          name: name
        }, {
          'headers': {
            'Authorization': 'headers.Authorization.token',
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        })
        .then(res => {        
          let markdownText = res.data;
          let rendererMD = new marked.Renderer();
          marked.setOptions({
            renderer: rendererMD,
            highlight: function(markdownText) {
              return hljs.highlightAuto(markdownText).value;
            },
            pedantic: false,
            gfm: true,
            tables: true,
            breaks: false,
            sanitize: false,
            smartLists: true,
            smartypants: false,
            xhtml: false
          });
          // 解析 markdown 为 html
          this.htmlText = marked(markdownText);
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
    }
  };
</script>


