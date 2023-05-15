<style>

</style>

<template>
    <div>
        <el-row>
            <el-col v-for="article in articleList" :key="article.id">
                <el-card>
                    <template #header>
                        <div>
                            <span>{{article.articleName}}</span>
                        </div>
                    </template>
                    <div>
                        {{ article.time }}
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>


<script type="text/ecmascript-6">

  import axios from "axios";

  export default {
    name: 'ArticleList',
    data() {
        return {
            articleList: [
                {
                    id: '1',
                    name: '2023-03-25-微信小程序实战.md',
                    articleName: '微信小程序实战',
                    time: '2023-03-25'
                },{
                    id: '2',
                    name: '2023-03-20-云计算安全.md',
                    articleName: '云计算安全',
                    time: '2023-03-20'
                },{
                    id: '3',
                    name: '2023-03-30-瑞吉外卖.md',
                    articleName: '瑞吉外卖',
                    time: '2023-03-30'
                }
            ]
        };
    },
    mounted() {

    } 
  };

</script>