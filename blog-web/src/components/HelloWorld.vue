<template>
  <el-container class="hello-world">
    <el-header class="header">
      <img src="../assets/logo2.png" class="header image" />
      <h1 style="font-size: 32px;margin: 10px; margin-right: 20%; color: gray;">
        <i>若令众生生欢喜者，则令一切如来欢喜</i>
      </h1>
    </el-header>
    <el-container>
      <el-aside class="left" :style="{'min-height':leftHeight}">
        <el-input placeholder="输入关键字进行过滤" v-model="filterText" style="border-color: gray;"></el-input>
        <el-tree
          class="filter-tree"
          :data="data"
          :props="defaultProps"
          highlight-current
          default-expand-all
          :expand-on-click-node="false"
          :indent="0"
          :filter-node-method="filterNode"
          icon-class="el-icon-arrow-right"
          @node-click="getDoc"
          ref="tree"
        >
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span v-if="data.depth>2">
              <i v-for="count in data.depth-2" class="tree-line" :key="count"></i>
              <i :class="data.joinIcon"></i>
              <i :class="data.icon"></i>
              {{ node.label }}
            </span>
            <span v-if="data.depth==2">
              <i :class="data.joinIcon"></i>
              <i :class="data.icon"></i>
              {{ node.label }}
            </span>
            <span v-if="!data.depth || data.depth<2">
              <i :class="data.icon"></i>
              {{ node.label }}
            </span>
          </span>
        </el-tree>
      </el-aside>
      <el-main class="main">
        <h2 class="title">{{info.title}}</h2>
        <p class="tips" v-show="info.publishedAt != null">{{updateTimeName + info.publishedAt}}</p>
        <hr v-if="info.title != null" />
        <div class="content" v-html="info.bodyHtml"></div>
      </el-main>
    </el-container>
    <el-footer style="height:40px">
      <p class="copyright">
        ©2019 aGirl.club 版权所有
        <a
          href="http://www.miitbeian.gov.cn/"
          style="color: #73777a;"
        >京ICP备18058123号-1</a>
      </p>
    </el-footer>
  </el-container>
</template>

<script>
import axios from "axios";
export default {
  name: "HelloWorld",
  data() {
    return {
      info: {},
      updateTimeName: "最后更新：",
      filterText: "",
      data: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      leftHeight: "500px",
      currentNodeId: null
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  mounted() {
    axios
      .get("/yuque/tree")
      .then(response => {
        this.data = response.data.data;
        this.initData();
      })
      .catch(function(error) {
        // 请求失败处理
        console.log(error);
      });
  },
  created() {
    this.leftHeight = window.innerHeight - 110 + "px";
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    initData() {
      this.data = this.initNodeList(this.data, 1);
    },
    initNodeList(data, depth) {
      let _this = this;
      let newData = [];
      data.forEach((node, index) => {
        node.depth = depth;
        if (depth == 1) {
          node.icon = "tree-book";
        } else if (node.children && node.children.length > 0) {
          node.icon = "tree-folder";
        } else {
          node.icon = "tree-item";
        }

        if (index < data.length - 1 || node.icon == "tree-folder") {
          node.joinIcon = "tree-join";
        } else {
          node.joinIcon = "tree-join-bottom";
        }

        if (node.children && node.children.length > 0) {
          node.children = _this.initNodeList(node.children, depth + 1);
        }
        newData.push(node);
      });
      return newData;
    },
    getDoc(data, node) {
      if(node.id === this.currentNodeId){
        return;
      }
      this.currentNodeId = node.id
      if (data && data.bookId && data.docId) {
        axios
          .get("/yuque/books/" + data.bookId + "/docs/" + data.docId)
          .then(response => {
            this.info = response.data.data;
          })
          .catch(function(error) {
            // 请求失败处理
            console.log(error);
          });
      } else {
        this.info = {
          title: data.name,
          bodyHtml: '<p class="copyright">暂无内容</p>'
        };
      }
    }
  }
};
</script>

<style scoped>
.header {
  /* background-color: #409EFF; */
  background-color: #ced0d1;
  margin-bottom: 10px;
}
.copyright {
  text-align: center;
  line-height: 30px;
  font-size: 14px;
  color: #73777a;
  letter-spacing: 0.5px;
  margin: 5px 0px;
}
.header .image {
  height: 60px;
  float: left;
  padding-left: 20px;
}

.left {
  /* background-color: #f3f3f3; */
  margin-left: 50px;
}
.main {
  /* background-color: #f3f3f3; */
  padding: 0;
  margin: 0px 50px 0px 50px;
}

.title {
  text-align: center;
  margin-top: 0;
  margin-right: 25%;
}
.tips {
  text-align: right;
  color: #000;
  padding-right: 30%;
}
.content {
  text-align: left;
  /* color: blue; */
  /* height: 10000px; */
}
.tree-book {
  padding-left: 16px;
  padding-left: 16px;
  background: url("../assets/folder.gif") left no-repeat;
}
.tree-folder-open {
  padding-left: 16px;
  background: url("../assets/folderopen.gif") left no-repeat;
}
.tree-item {
  padding-left: 16px;
  background: url("../assets/item.gif") left no-repeat;
}
.tree-folder {
  padding-left: 16px;
  background: url("../assets/folderopen.gif") left no-repeat;
}
.tree-line {
  padding-left: 18px;
  background: url("../assets/line.gif") left no-repeat;
}
.tree-join {
  padding-left: 18px;
  background: url("../assets/join.gif") left no-repeat;
}
.tree-join-bottom {
  padding-left: 18px;
  background: url("../assets/joinbottom.gif") left no-repeat;
}
.custom-tree-node {
  font-size: 14px;
}
</style>
