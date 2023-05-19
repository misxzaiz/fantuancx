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
    <!-- <Page></Page> -->
    <!-- <Demo></Demo> -->
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
        <!-- 添加、删除、更新用户对话框 -->
        <div>
            <el-dialog v-model="dialogVisible" :title="dialogTitle" :destroy-on-close="true" >
                <el-form ref="userForm" :model="userForm" label-width="100px">
                    <el-form-item label="账号">
                        <el-input v-model="userForm.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="userForm.password"></el-input>
                    </el-form-item>
                    <el-form-item label="电话">
                        <el-input v-model="userForm.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱">
                        <el-input v-model="userForm.email"></el-input>
                    </el-form-item>
                    <el-form-item label="头像">
                        <el-input v-model="userForm.avatar"></el-input>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span>
                        <el-button @click="dialogVisible = false">关闭</el-button>
                        <el-button type="primary" @click="handleDialogAction">{{ dialogAction }}</el-button>
                    </span>
                </template>
            </el-dialog>
        </div>
    </div>
</template>
  
<script type="text/ecmascript-6">

    import axios from 'axios'
    import Page from './page.vue'
    import Demo from './demo.vue'

    export default {
        data() {
            return {
                users: [],
                currentPage: 1,
                pages: 100,
                dialogVisible: false,
                dialogTitle: '',
                userForm: {},
                selectedRows: [], // 存储被选中的行数据
                dialogAction: '', // 当前操作名称
                selectedRows: [], // 存储被选中的行数据
                // 请求头
                headers: {
                    'headers': {
                        'Authorization': 'headers.Authorization.token'
                    }
                },
            };
        },
        components: {
            Page,
            Demo
        },
        mounted() {  
            // 获取 token
            this.headers.headers.Authorization = localStorage.getItem('token')
            // 获取第一页数据
            this.getCurrentPage(this.currentPage)
        },
        methods: {
            // 获取当前页数据
            getCurrentPage(currentPage){
                axios.get(window.$uriReq+'/userservice/user/pages/' + currentPage,this.headers)
                .then(response => {
                    this.users = response.data.data.users
                    // 注意类型
                    this.pages = parseInt(response.data.data.pages)
                    this.currentPage = currentPage
                })
                .catch(error => {
                    console.log(error)
                })
            },
            // 更新多选的数据
            handleSelectionChange(selectionRows) {
                this.selectedRows = selectionRows; // 更新选中的行数据
            },
            // 批量删除
            handleBatchDelete() {
                if (this.selectedRows.length === 0) {
                    this.$message.warning('请先选择要删除的数据');
                    return;
                }

                this.$confirm('确定要删除选中的数据吗？', '提示', { 
                    type: 'warning'
                    }).then(() => {
                    // 获取选中的行的 Id
                    const ids = this.selectedRows.map(row => row.id);
                    // 获取 token
                    const token = localStorage.getItem('token');
                    // 发送删除请求到 /user/batch-delete 接口
                    axios.post(window.$uriReq+'/userservice/user/batch-delete', 
                        ids
                        , {
                            headers: {
                            'Authorization': `${token}`
                            }
                        }).then(() => {
                            // 如果成功则更新表格数据
                            this.users = this.users.filter(user => !ids.includes(user.id));
                            this.selectedRows = [];
                            this.$message.success('删除成功');
                            this.getCurrentPage(this.currentPage)
                        }).catch(() => {
                            // 如果失败则给出提示信息
                            this.$message.error('删除失败，请稍后重试');
                    });
                }).catch(() => {});
            },
            // 判断对话框请求
            handleDialogAction() {
                if (this.dialogAction === '保存') {
                    this.saveUser();
                } else if (this.dialogAction === '更新') {
                    this.updateUser();
                } else if (this.dialogAction === '删除') {
                    this.deleteUser();
                }
            },
            // 点击编辑按钮
            handleEdit(row) {
                this.dialogTitle = '修改用户信息';
                this.dialogAction = '更新';
                this.dialogVisible = true;
                //this.userForm = {...row}
                this.userForm = Object.assign({}, row);
            },
            // 点击删除按钮
            handleDelete(row) {
                this.dialogTitle = '确定删除用户';
                this.dialogAction = '删除';
                this.dialogVisible = true;
                this.userForm = {...row}
            },
            // 点击添加按钮
            handleAdd() {
                this.dialogTitle = '添加用户';
                this.dialogAction = '保存';
                this.dialogVisible = true;
                this.userForm = {};
            },
            // 保存用户
            saveUser() {
                const url = window.$uriReq + '/userservice/user';
                axios.post(url, this.userForm, this.headers )
                .then(res => {
                    this.res(res)
                }).catch(error => {
                    this.dialogVisible = false;
                })
            },
            // 更新用户信息
            updateUser() {
                console.log(this.headers)
                const url = window.$uriReq + '/userservice/user';
                axios.put(url, this.userForm, this.headers )
                .then(res => {
                    this.res(res)
                }).catch(error => {
                    alert(error)
                    this.dialogVisible = false;
                })
            },
            // 删除用户
            deleteUser() {
                const url = window.$uriReq + '/userservice/user/'+this.userForm.id;
                axios.delete(url,  this.headers)
                .then(res => {
                    this.res(res)
                }).catch(error => {
                    this.dialogVisible = false;
                })
            },
            // 处理正确响应结果
            res(res) {
                if(res.data.code === 200 ){
                    this.dialogVisible = false;
                    this.$message({
                        message: res.data.message,
                        type: 'success'
                    })
                    this.getCurrentPage(this.currentPage)
                }else {
                    this.$message({
                        message: res.data.message,
                        type: 'error'
                    })                        
                }
            },
        }
    };
</script>
  