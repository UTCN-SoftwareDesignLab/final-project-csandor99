<template>
  <v-card>
    <v-card-title>
      Items
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Item</v-btn>
      <v-btn @click="viewUsers">View Users</v-btn>
      <v-btn @click="reportOutOfStockPDF"
        >Download<br />Out of Stock Report</v-btn
      >
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";
import router from "@/router";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "ItemList",
  components: { ItemDialog },
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
      ],
      dialogVisible: false,
      selectedItem: {},
      connected: false,
    };
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    viewUsers() {
      this.disconnect();
      router.push("/users");
    },
    reportOutOfStockPDF() {
      api.items.generateReport("PDF");
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    toast(response) {
      this.$toasted.error(response, {
        theme: "toasted-primary",
        position: "bottom-right",
        duration: 5000,
      });
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
  },
  created() {
    this.refreshList();
    this.socket = new SockJS("http://localhost:8091/gs-guide-websocket");
    this.stompClient = Stomp.over(this.socket);
    this.stompClient.connect(
      {},
      (frame) => {
        this.connected = true;
        console.log(frame);
        this.stompClient.subscribe("/topic/messages", (tick) => {
          this.toast(tick.body);
          console.log(tick);
        });
      },
      (error) => {
        console.log(error);
        this.connected = false;
      }
    );
  },
};
</script>

<style scoped></style>
