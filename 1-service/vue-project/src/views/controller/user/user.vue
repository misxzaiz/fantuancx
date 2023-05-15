<style>
    .table {
        width: 100%;
        margin: 1%;
    }
</style>

<template>
    <div class="table">
        <!-- 添加用户 -->
        <el-button @click="handleSave()">添加用户</el-button>
        <!-- 添加用户表单 -->
        <el-dialog v-model="dialogVisibleSave" :title="dialogTitleSave" :destroy-on-close="true" >
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
                    <el-button @click="dialogVisibleSave = false">关闭</el-button>
                    <el-button type="primary" @click="saveUser">保存</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 用户列表 -->
        <el-table :data="users">
            <el-table-column fixed prop="username" label="用户名" width="100"></el-table-column>
            <el-table-column prop="password" label="密码" width="100"></el-table-column>
            <el-table-column prop="phone" label="电话" width="130"></el-table-column>
            <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
            <el-table-column prop="avatar" label="头像" width="80">
                <template #default="{row}">
                    <img v-if="row.avatar" :src="row.avatar" style="width: 50px; height: 50px;">
                    <span v-else>无</span>
                </template>
            </el-table-column>
            <el-table-column prop="createdAt" label="创建时间" width="250"></el-table-column>
            <el-table-column prop="updatedAt" label="更新时间" width="250"></el-table-column>
            <el-table-column label="操作" fixed="right" width="150">
                <template #default="{row}">
                    <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
                </template> 
            </el-table-column>
        </el-table>
        <!-- 修改用户信息 -->
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
                    <el-button type="primary" @click="updateUser">保存</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 确定删除用户 -->
        <el-dialog v-model="dialogVisibleDelete" :title="dialogTitleDelete" :destroy-on-close="true">
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
                    <el-button @click="dialogVisibleDelete = false">关闭</el-button>
                    <el-button type="primary" @click="deleteUser">确定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
  
<script type="text/ecmascript-6">

    import axios from 'axios'

    export default {
        data() {
            return {
                users: [],
                dialogVisible: false,
                dialogVisibleSave: false,
                dialogVisibleDelete: false,
                dialogTitle: '',
                dialogTitleSave: '',
                dialogTitleDelete: '',
                userForm: {},
            };
        },
        mounted() {  
            this.getUser()
        },
        methods: {
            getUser(){
                let that = this;
                // 禁止直接跳转
                const token = localStorage.getItem('token');
                if(token==='null' || token === null){
                    this.$router.push('/login')
                }
                axios.get(window.$uriReq+'/user',{
                    headers: {
                        'Authorization': `${token}`
                    }
                })
                .then(res => {
                    that.users = res.data.data
                    console.log(res.data.data)
                })
            },
            handleEdit(row) {
                // 设置对话框标题
                this.dialogTitle = '编辑用户';
                // 复制一份数据，避免直接修改原数据
                this.userForm = {...row}
                // 打开对话框
                this.dialogVisible = true
            },
            updateUser(){
                let that = this;
                // 提交表单数据，并更新用户信息
                const url = window.$uriReq + '/user';
                const token = localStorage.getItem('token');
                axios.put(url, this.userForm, {
                    headers: {
                        'Authorization': `${token}`
                    }
                })
                .then(res => {
                    if(res.data.code === 200 ){
                        this.dialogVisible = false;
                        this.$message({
                            message: res.data.message,
                            type: 'success'
                        })
                        that.getUser()
                    }else {
                        this.$message({
                            message: res.data.message,
                            type: 'error'
                        })                        
                    }
                }).catch(error => {
                    this.dialogVisible = false;
                })
            },
            handleSave() {
                // 设置对话框标题
                this.dialogTitleSave = '新增用户';
                // 复制一份数据，避免直接修改原数据
                this.userForm = {}
                // 打开对话框
                this.dialogVisibleSave = true
            },
            saveUser(){
                let that = this;
                // 提交表单数据，并更新用户信息
                const url = window.$uriReq + '/user';
                const token = localStorage.getItem('token');
                axios.post(url, this.userForm, {
                    headers: {
                        'Authorization': `${token}`
                    }
                })
                .then(res => {
                    if(res.data.code === 200 ){
                        this.dialogVisibleSave = false;
                        this.$message({
                            message: res.data.message,
                            type: 'success'
                        })
                        that.getUser()
                    }else {
                        this.$message({
                            message: res.data.message,
                            type: 'error'
                        })                        
                    }

                }).catch(error => {
                    this.dialogVisible = false;
                })
            },
            handleDelete(row) {
                // 设置对话框标题
                this.dialogTitleDelete = '删除用户';
                // 复制一份数据，避免直接修改原数据
                this.userForm = { ...row }
                // 打开对话框
                this.dialogVisibleDelete = true
            },
            deleteUser(){

                let that = this;
                // 提交表单数据，并更新用户信息
                const url = window.$uriReq + '/user/'+this.userForm.id;
                const token = localStorage.getItem('token');
                axios.delete(url, {
                    headers: {
                        'Authorization': `${token}`
                    }
                })
                .then(res => {
                    this.dialogVisibleDelete = false;
                    this.$message({
                        message: res.data.message,
                        type: 'success'
                    })
                    that.getUser()
                }).catch(error => {
                    this.dialogVisible = false;
                })
            }
        }
    };
</script>
  