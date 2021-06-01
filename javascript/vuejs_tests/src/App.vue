<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">

    <table align="center" border="1">
      <tr>
        <td><HelloWorld link="http://vuejs.org"/></td>
      </tr>
      <tr>
        <td><TwoWayBinding/></td>
      </tr>
      <tr>
        <td><ConditionalRendering/></td>
      </tr>
      <tr>
        <td><ListRendering/></td>
      </tr>
      <tr>
        <td><ClassStyleBinding/></td>
      </tr>
      <tr>
        <td><ComputedProperties firstname="Aaron" lastname="Yee"/></td>
      </tr>
      <tr>
        <td><EventHandling/></td>
      </tr>
      <tr>
        <td><FormInputBinding/></td>
      </tr>
    </table>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import TwoWayBinding from './components/TwoWayBinding.vue'
import ConditionalRendering from './components/ConditionalRendering.vue'
import ListRendering from './components/ListRendering.vue'
import ComputedProperties from "@/components/ComputedProperties"
import ClassStyleBinding from "@/components/ClassStyleBinding"
import EventHandling from "@/components/EventHandling"
import FormInputBinding from "@/components/FormInputBinding";
export default {
  name: 'App',
  components: {
    HelloWorld,TwoWayBinding,ConditionalRendering,ListRendering, ComputedProperties, ClassStyleBinding,EventHandling, FormInputBinding
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
