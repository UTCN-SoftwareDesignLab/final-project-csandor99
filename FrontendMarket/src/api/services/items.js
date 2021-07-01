import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/items", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(item) {
    return HTTP.post(BASE_URL + "/items", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(item) {
    return HTTP.patch(BASE_URL + "/items", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    return HTTP.delete(BASE_URL + "/items/" + item.id, {
      headers: authHeader(),
    }).then();
  },
  filteredItems(filter) {
    return HTTP.get(BASE_URL + "/items/search/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sell(name, list) {
    return HTTP.patch(
      BASE_URL + "/items/sell/" + name,
      list,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  generateReport(type) {
    return HTTP.get(BASE_URL + "/items/export/" + type, {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      document.body.appendChild(link);
      link.href = url;
      link.setAttribute("download", "OutOfStockItems.pdf");
      link.click();
    });
  },
};
