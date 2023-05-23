<style>
    .table {
        width: 100%;
        margin: 1%;
    }
    .user-container {
        margin: 1%;
    }
    .user-nav {
        margin: 1%;
    }
</style>

<template>
    <div class="user-container">
        <!-- 添加/批量删除用户 -->
        <div class="user-nav">
            <el-button @click="handleAdd()">添加用户</el-button>
            <el-button type="danger"  @click="handleBatchDelete">批量删除</el-button>
        </div>
        <!-- 用户列表 -->
        <div>
            <el-table :data="users" @selection-change="handleSelectionChange" >
                <el-table-column type="selection" />
                <el-table-column fixed="left" prop="username" label="用户名" />
                <el-table-column prop="password" label="密码" />
                <el-table-column prop="phone" label="电话" />
                <el-table-column prop="email" label="邮箱" />
                <el-table-column prop="avatar" label="头像" width="80">
                    <template #default="{row}">
                        <img v-if="row.avatar" :src="row.avatar" style="width: 50px; height: 50px;">
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column prop="createdAt" label="创建时间" />
                <el-table-column prop="updatedAt" label="更新时间" />
                <el-table-column label="操作" fixed="right" width="150">
                    <template #default="{row}">
                        <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
                        <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
                    </template> 
                </el-table-column>
            </el-table>
            <el-pagination 
                layout="prev, pager, next"  
                :current-page="currentPage"
                :page-count="pages" 
                @current-change="getCurrentPage"
                />
        </div>

    </div>

</template>
  
<script type="text/ecmascript-6">

    import axios from 'axios'

    export default {
        data() {
            return {
                users: [],
                // 请求头
                headers: {
                    'headers': {
                        'Authorization': 'headers.Authorization.token'
                    }
                },

            };
        },
        components: {

        },
        mounted() {  
            // 获取 token
            this.headers.headers.Authorization = localStorage.getItem('token')
            this.getCurrentPage(1)
        },
        methods: {
            getCurrentPage(currentPage){
                axios.get(window.$uriReq+'/userservice/user/pages/' + currentPage,this.headers)
                .then(response => {
                    this.users = response.data.data.users
                    this.pages = parseInt(response.data.data.pages)
                    
                })
                .catch(error => {
                    console.log(error)
                })
            },
        }
    };
</script>
  