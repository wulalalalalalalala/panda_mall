<template>
  <v-card>
    <v-card-title>
      <v-btn color="primary" @click="addBrand">新增品牌</v-btn>
      <!--搜索框，与search属性关联-->
      <v-spacer/><!--空格-->
      <v-flex xs3>
        <!--append-icon="search"：搜索图标 hide-details：上下距离紧凑-->
        <v-text-field label="输入品牌名称或首字母搜索" v-model.lazy="search" append-icon="search" hide-details/>
      </v-flex>
    </v-card-title>
    <v-divider/><!--分割线-->
    <v-data-table
      :headers="headers"
      :items="brands"
      :pagination.sync="pagination"
      :total-items="totalBrands"
      :loading="loading"
      class="elevation-1"
    >
      <template slot="items" slot-scope="props">
        <td class="text-xs-center">{{ props.item.id }}</td>
        <td class="text-xs-center">{{ props.item.name }}</td>
        <td class="text-xs-center">
          <img v-if="props.item.image" :src="props.item.image" width="130" height="40">
          <span v-else>无</span>
        </td>
        <td class="text-xs-center">{{ props.item.letter }}</td>
        <td class="justify-center layout px-0">
          <v-btn icon @click="editBrand(props.item)">
            <i class="el-icon-edit"/>
          </v-btn>
          <v-btn icon @click="deleteBrand(props.item)">
            <i class="el-icon-delete"/>
          </v-btn>
        </td>
      </template>
    </v-data-table>
    <!--弹出的对话框v-dialog-->
    <v-dialog max-width="500" v-model="show" persistent scrollable><!--persistent点击对话框外部不能使其关闭-->
      <v-card>
        <!--对话框的标题-->
        <v-toolbar dense dark color="primary">
          <v-toolbar-title>{{isEdit ? '修改' : '新增'}}品牌</v-toolbar-title>
          <v-spacer/>
          <!--关闭窗口的按钮-->
          <v-btn icon @click="closeWindow"><!--icon变成⚪图标-->
            <v-icon>close</v-icon><!--⚪图标里面的图案，其他如search变成🔍-->
          </v-btn>
        </v-toolbar>
        <!--对话框的内容，表单-->
        <v-card-text class="px-5" style="height:400px">
          <brand-form @close="closeWindow" :oldBrand="oldBrand" :isEdit="isEdit"/>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
  // 导入自定义的表单组件
  import BrandForm from './BrandForm'

  export default {
    name: "brand",
    data() {
      return {
        search: '', // 搜索过滤字段
        totalBrands: 0, // 总条数
        brands: [], // 当前页品牌数据
        loading: true, // 是否在加载中
        pagination: {}, // 分页信息
        headers: [
          {text: 'id', align: 'center', value: 'id'},
          {text: '名称', align: 'center', sortable: false, value: 'name'},
          {text: 'LOGO', align: 'center', sortable: false, value: 'image'},
          {text: '首字母', align: 'center', value: 'letter', sortable: true,},
          {text: '操作', align: 'center', value: 'id', sortable: false}
        ],
        show: false,// 控制对话框的显示
        oldBrand: {}, // 即将被编辑的品牌数据
        isEdit: false, // 是否是编辑
      }
    },
    mounted() { // 渲染后执行
      // 查询数据
      this.getDataFromServer();
    },
    watch: {
        pagination: { // 监视pagination属性的变化
          deep: true, // deep为true，会监视pagination的属性及属性中的对象属性变化
          handler() {
            // 变化后的回调函数，这里我们再次调用getDataFromServer即可
            this.getDataFromServer();
          }
        },
        search: { // 监视搜索字段
          handler() {
            this.getDataFromServer();
          }
        }
    },
    methods: {
      getDataFromServer() { // 从服务的加载数的方法。
        // 发起请求
        this.$http.get("/item/brand/page", {
          params: {
            key: this.search, // 搜索条件
            page: this.pagination.page,// 当前页
            rows: this.pagination.rowsPerPage,// 每页大小
            sortBy: this.pagination.sortBy,// 排序字段
            desc: this.pagination.descending// 是否降序
          }
        }).then(resp => { // 这里使用箭头函数
          this.brands = resp.data.items;
          this.totalBrands = resp.data.total;
          // 完成赋值后，把加载状态赋值为false
          this.loading = false;
        })
      },
      addBrand() {
        // 修改标记
        this.isEdit = false;
        // 控制弹窗可见：
        this.show = true;
        // 把oldBrand变为null
        this.oldBrand = null;
      },
      editBrand(oldBrand) {
        // 根据品牌信息查询商品分类
        this.$http.get("/item/category/bid/" + oldBrand.id)
          .then(({data}) => {
            // 修改标记
            this.isEdit = true;
            // 控制弹窗可见：
            this.show = true;
            // 获取要编辑的brand
            this.oldBrand = oldBrand
            // 回显商品分类
            this.oldBrand.categories = data;
          })
      },
      closeWindow() {
        // 关闭窗口
        this.show = false;
        // 关闭窗口重新加载数据
        this.getDataFromServer();
      }
    },
    components: {
      //引入BrandForm还需将其注册成组件
      BrandForm
    }
  }
</script>
<style scoped>
</style>
