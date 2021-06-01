<template>
  <div id="form">
    <div id="textfield">
      <input v-model="message" placeholder="edit me"/>
      <p>message is : {{message}}</p>
    </div>
    <hr/>
    <div id="v-model-multiple-checkboxes">
      <input type="checkbox" id="jack" value="Jack" v-model="checkedNames" />
      <label for="jack">Jack</label>
      <input type="checkbox" id="john" value="John" v-model="checkedNames" />
      <label for="john">John</label>
      <input type="checkbox" id="mike" value="Mike" v-model="checkedNames" />
      <label for="mike">Mike</label>
      <br />
      <span>Checked names: {{ checkedNames }}</span>
    </div>
    <hr/>
    <div id="v-model-radiobutton">
      <input type="radio" id="one" value="One" v-model="picked" />
      <label for="one">One</label>
      <br />
      <input type="radio" id="two" value="Two" v-model="picked" />
      <label for="two">Two</label>
      <br />
      <span>Picked: {{ picked }}</span>
    </div>
    <hr/>

    <div id="v-model-select" class="demo">
      <select v-model="selected">
        <option disabled value="">Please select one</option>
        <option>A</option>
        <option>B</option>
        <option>C</option>
      </select>
      <span>Selected: {{ selected }}</span>
    </div>

  </div>

</template>

<script>
export default {
  name: "FormInputBinding",
  data(){
    return {
      message:"",
      checkedNames: [],
      picked : "",
      selected: ""
    }
  }
}
</script>

<style scoped>

</style>