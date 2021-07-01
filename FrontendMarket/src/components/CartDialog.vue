<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Shopping cart </v-toolbar>
        <ul>
          <li v-for="(value,index) in list"
              :key="index">
            {{value.name}}
          </li>
        </ul>
        <v-card-actions>
          <v-btn @click="persist"> Sell </v-btn>
          <p>Total: {{computedTotal}}</p>
        </v-card-actions>
        <p>{{ offer ? " Offer 20%" : " No offer" }} </p>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "CartDialog",
  props: {
    list: Array,
    opened: Boolean,
    offer: Boolean
  },
  methods: {
    persist() {
      api.items
        .sell(this.$store.getters["auth/getName"], this.list)
        .then(() => this.$emit("refresh"))
    },
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
    computedTotal: function () {
      const sum = this.list.map(element => element.price).reduce((a, b) => a + b, 0);
      if(this.offer){
        return sum -(sum * 0.2);
      } else {
        return sum;
      }

    },
  },
};
</script>

<style scoped></style>
