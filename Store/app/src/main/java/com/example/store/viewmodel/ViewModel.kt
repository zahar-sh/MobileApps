package com.example.store.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import android.net.ConnectivityManager
import com.example.store.app.App
import com.example.store.dao.AppDatabase
import com.example.store.dao.AppDatabaseHistory
import com.example.store.dao.HistoryItemDao
import com.example.store.dao.ItemDao
import com.example.store.entity.HistoryItem
import com.example.store.entity.Item


class ViewModel() : ViewModel() {

    var filt = 0
    var itemList = MutableLiveData<List<Item>>()
    var historyitemList = MutableLiveData<List<HistoryItem>>()
    var creatHistoryItemList: MutableList<HistoryItem> = mutableListOf()
    var creatItemList: MutableList<Item> = mutableListOf()
    var mainItemList: MutableList<Item> = mutableListOf()
    var localitem: MutableLiveData<Item> = MutableLiveData()
    var filttext = ""
    var filtPrice1 = ""
    var filtPrice2 = ""
    val db: AppDatabase? = App.instance?.database
    val itemDao: ItemDao = db?.itemDao()!!

    val dbHistory: AppDatabaseHistory? = App.instance?.databaseHistory
    val historyItemDao: HistoryItemDao = dbHistory?.historyItemDao()!!
    lateinit var doc: Document


    fun getdoc(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isOnline(context)) {
                for (j in 1..10) {
                    var str = "https://www.21vek.by/clothes/page:"
                    str = str + j.toString() + "/"
                    doc =
                        Jsoup.connect(str)//"https://www.21vek.by/clothes/?filter%5Bprice%5D%5Bfrom%5D=5&filter%5Bprice%5D%5Bto%5D=194&filter%5Bsa%5D=")
                            .get()

                    val elements1: Elements? =
                        doc.getElementsByClass("result__item cr-result__full  g-box_lseparator g-box_lseparator_catalog")

                    if (elements1 != null) {
                        for (element in elements1) {
                            val doc2: Document =
                                Jsoup.connect(
                                    (element.getElementsByAttributeValue(
                                        "data-ga_category",
                                        "Navigation"
                                    ).attr("href"))
                                )
                                    .get()
                            // creatItemList.add(
                            itemDao.insert(
                                Item(
                                    id = 0,
                                    href = element.getElementsByAttributeValue(
                                        "data-ga_category",
                                        "Navigation"
                                    ).attr("href"),
                                    name = element./*getElementsByClass(" g-price result__price cr-price__in")[0].*/getElementsByTag(
                                        "span"
                                    ).attr("data-name"),
                                    img = doc2.getElementsByClass("b-content")[0].getElementsByClass(
                                        "l-photo"
                                    )[0].getElementsByTag(
                                        "img"
                                    ).attr("src"),
                                    //elements1[element].getElementsByClass("result__img")[0].getElementsByTag("img").attr("src"),
                                    price = element./*getElementsByClass(" g-price result__price cr-price__in")[0].*/getElementsByTag(
                                        "span"
                                    ).attr("data-price").toDouble(),
                                    producer_name = element./*getElementsByClass(" g-price result__price cr-price__in")[0].*/getElementsByTag(
                                        "span"
                                    ).attr("data-producer_name"),
                                    type = element.getElementsByClass("result__attrs")[0].getElementsByTag(
                                        "tr"
                                    )[0].getElementsByTag("td")[1].ownText(),
                                    about = element.getElementsByClass("result__attrs")[0].text()
                                )
                            )
                            creatItemList = itemDao.getAll() as MutableList<Item>
                            viewModelScope.launch(Dispatchers.Main) {
                                setItem()
                            }
                        }
                    }
                }
            } else {
                creatItemList = itemDao.getAll() as MutableList<Item>
                viewModelScope.launch(Dispatchers.Main) {
                    setItem()
                    val toast = Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }

    fun setItem() {
        if (filt == 0) {
            mainItemList = creatItemList
        } else if (filt == 1) {
            mainItemList = creatItemList.filter {
                it.name.contains(
                    filttext,
                    ignoreCase = true
                ) || (it.producer_name.contains(filttext, ignoreCase = true)) ||
                        (it.type.contains(filttext, ignoreCase = true))
            } as MutableList<Item>
        } else {
            mainItemList =
                creatItemList.filter { (it.price > (filtPrice1.toDouble())) && (it.price < (filtPrice2.toDouble())) } as MutableList<Item>
        }
        itemList.value = mainItemList
    }

    fun getItem(): LiveData<List<Item>> {
        return itemList
    }

    fun filter(text: String) {
        filttext = text
        filt = 1
        setItem()
    }

    fun filterWoman() {
        filttext = "женщины"
        filt = 1
        setItem()
    }

    fun filterMan() {
        filttext = "мужчины"
        filt = 1
        setItem()
    }

    fun filterPrice(price1: String, price2: String) {
        filtPrice1 = price1
        filtPrice2 = price2
        filt = 2
        setItem()
    }

    fun deleteFilter() {
        filt = 0
        setItem()
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }


    fun initial(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            creatItemList = itemDao.getAll() as MutableList<Item>
            for (i in creatItemList) {
                if (i.id == id) {
                    viewModelScope.launch(Dispatchers.Main) {
                        localitem.value = i
                    }
                    break
                }
            }
        }
    }

    fun getOneItem(): MutableLiveData<Item> {
        return localitem
    }

    fun addHistory(historyItem: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            historyItemDao.insert(historyItem)
        }
    }

    fun initialHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            creatHistoryItemList = historyItemDao.getAll() as MutableList<HistoryItem>
            viewModelScope.launch(Dispatchers.Main) {
                historyitemList.value = creatHistoryItemList
            }
        }
    }

    fun getHistoryItem(): LiveData<List<HistoryItem>> {
        return historyitemList
    }

    fun getSharedPreference(context: Context, KEY_NAME: String): String {
        val sharedPreference: SharedPreference = SharedPreference(context)
        return sharedPreference.getValueString(KEY_NAME).toString()
    }

    fun putSharedPreference(context: Context, KEY_NAME: String, text: String) {
        val sharedPreference: SharedPreference = SharedPreference(context)
        sharedPreference.save(KEY_NAME, text)
    }
}

