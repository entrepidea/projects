<template>
  <div class="hello">
    <h1><a v-bind:href="link">{{ msg }}</a></h1>
    <br/>
    <h2><span v-html="msg2"/></h2>
    <p>
      <button v-on:click="changeMessage">Change message</button>
      <button v-on:click="reverseMessage">Reverse Message</button>
    </p>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    link:String
  },
  data(){
    return {
      msg:"Welcome to your Vue.js App",
      msg2:"<span style=\"color:red\">this is another message</span>" //see how directive v-html interprets a html string
    };
  },
  methods:{
    changeMessage(){
      this.msg = "Vue.js is awesome";
    },
    reverseMessage(){
      this.msg = this.msg
          .split('')
          .reverse()
          .join('')
      //work on msg2 too
      this.msg2 = this.msg2
          .split('')
          .reverse()
          .join('')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
