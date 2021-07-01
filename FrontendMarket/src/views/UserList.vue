<template>
  <v-card>
    <Toasts> </Toasts>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add User</v-btn>
      <v-btn @click="viewItems">View Items</v-btn>
      <v-btn @click="reportActivityPDF">Download<br />Activity Report</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedItem"
      @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import router from "@/router";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        { text: "Phone", value: "phone" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedItem: {},
      connected: false,
    };
  },
  methods: {
    addUser() {
      this.dialogVisible = true;
    },
    editUser(user) {
      this.selectedItem = user;
      this.dialogVisible = true;
    },
    viewItems() {
      router.push("/items");
      this.disconnect();
    },
    reportActivityPDF() {
      api.activity.generateActivityReport("PDF");
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    toast(response){
      this.$toasted.error(response, {
        theme: "toasted-primary",
        position: "bottom-right",
        duration: 5000,
      });
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.users = await api.users.allUsers();
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
