<style>
  .contentNav {
    margin: 5%;
    
  }
  .nav {
    overflow: auto;
  }
  .contentTitle {
    margin: 5% 0 5% 0;
  }
  .title1 {
    font-weight: bolder;
    font-size: larger;
  }
  .title2 {
    font-size: large;
  }
  pre {
    background: #000;
    overflow: auto;
  }

  code {
    color: #fff;
  }
</style>

<template>
  <el-row :gutter="20">
    <el-col :span="6" class="nav">
      <div class="contentNav">
        <div v-for="nav in contentNav" :key="nav.level" class="contentTitle">
          <div v-if="nav.level === 1" class="title1">{{ nav.title }}</div>
          <div v-if="nav.level === 2" class="title2">{{ nav.title }}</div>
          <!-- <div v-if="nav.level === 3">{{ nav.title }}</div> -->
        </div>
      </div>
    </el-col>
    <el-col :span="18"><div v-html="htmlText"></div></el-col>
  </el-row>
</template>

<script>
import { marked } from 'marked';
import { mangle } from 'marked-mangle';
import { gfmHeadingId } from 'marked-gfm-heading-id';
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
        // 解析 markdown 为 html
        this.htmlText = marked(markdownText);
        // 解析 markdown 标题
        this.getContentNav(markdownText)
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
    },
    // 获取标题
    getContentNav(markdownText) {
      // 匹配所有标题的正则
      // let reg = /(#{1,6})\s(.*)/g;
      let reg = /(#{1,6})\s(.*)/g;
      // 匹配到的所有标题
      let titleArr = markdownText.match(reg);
      // 获取所有标题的级别和标题
      titleArr.map(item => {
        return item.replace(reg, (match, p0, p1) => {
          this.contentNav = this.contentNav.concat({
            level: p0.length,
            title: p1
          })
        })
      })
    }
  }
};
</script>


