<template>
  <div id="app">
    <HelloWorld/>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld'

export default {
  name: 'App',
  components: {
    HelloWorld
  }
}
</script>

<style>
body {
  margin: 0;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: rgb(225, 243, 216) !important;
}
.el-tree-node__content {
  height: 18px !important;
}
.el-input__inner,.el-tree {
  /* background-color: #f3f3f3 !important; */
  background-color: rgb(225, 243, 216) !important;
}
.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
  /* background-color: rgb(250, 236, 216) !important; */
  background-color: #E6A23C !important;
}
.el-tree-node__content:hover {
  /* background-color: rgb(250, 236, 216) !important; */
  background-color: #E6A23C !important;
}
</style>
