<template>
  <v-card>
    <Toasts> </Toasts>
    <v-card-title>
      Checkpoint
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="searchBook">Search</v-btn>
      <v-btn @click="viewCart">Go to cart</v-btn>
    </v-card-title>
    <v-data-table :headers="headers" :items="items">
      <template v-slot:item.action="{ item }">
        <v-btn color="success" @click="addToCart(item)">Add</v-btn>
      </template>
    </v-data-table>
    <CartDialog
      :opened="dialogVisible"
      :list="list"
      :offer="isOffer"
      @refresh="refreshList"
    ></CartDialog>
  </v-card>
</template>

<script>
import api from "../api";
import CartDialog from "../components/CartDialog";

export default {
  name: "ItemListEmployee",
  components: { CartDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Barcode", value: "barcode" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
        { text: "Actions", value: "action", sortable: false, align: "center" },
      ],
      dialogVisible: false,
      isOffer: false,
      list: [],
    };
  },
  methods: {
    addToCart(item) {
      if(item.quantity == 0){
        this.toastError("Item not in stock")
      }else{
        this.list.push(item);
        this.toast(item.name + " added to cart!");
      }

    },
    viewCart() {
      this.dialogVisible = true;
      this.computeOffer();
    },
    toast(response){
      this.$toasted.show(response, {
        theme: "toasted-primary",
        position: "bottom-right",
        duration: 5000,
      });
    },
    toastError(response){
      this.$toasted.error(response, {
        theme: "toasted-primary",
        position: "bottom-right",
        duration: 5000,
      });
    },
    async computeOffer() {
      let nr = await api.activity.countEmployeeActivity(
        this.$store.getters["auth/getName"]
      );
      console.log(nr);
      if ((nr + 1) % 10 == 0) {
        this.isOffer = true;
      } else {
        this.isOffer = false;
      }
      console.log(this.isOffer);
    },
    async searchBook() {
      if (this.search === "") {
        this.items = await api.items.allItems();
      } else {
        this.items = await api.items.filteredItems(this.search);
      }
    },
    async refreshList() {
      this.dialogVisible = false;
      this.items = await api.items.allItems();
      this.list = [];
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
